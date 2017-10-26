/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import constants.ReservedWords;
import constants.TokenType;
import Lexer.LexicalExceptions.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class Lexer {
    // sentinel int for end of file
    final static int EOF = -1;
    // sentinel char for end of file
    final static char EOF_CHAR = '\0';
    // buffer size
    final static int BUFFER_SIZE = 1024;
    // maximum identifier length
    final static int MAX_IDENTIFIER_SIZE = 32;
    // valid chars
    final static String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890" +
                                      ".,;:<>/*[]+-=()}{\t ";

    static boolean isValidInput(char c) { return Lexer.VALID_CHARS.indexOf(c) != -1; }
    
    // Track the line number
    private int line = 1;
    
    // The filename that lexical analyzer is reading.
    private String filename = null;
    
    // lexeme buffer
    private StringBuilder lexemeBuilder; 
    private String lastLexeme = "";
    // handling peeking
    private Stack<Character> pushback;
    // stream
    private InputStream stream;
    // hold lookahead char
    private char peek;
    // Peeker class which is responsible for circular input buffering 
    private Peeker peeker;
    // hold last token
    private Token lastToken;
    
    // Responsible for input buffering (multiway buffering)
    private class Peeker {
        // sentinul for end of buffer
        final static int EOB = -2;
        // ways, number of buffers
        // lookahead, simulate pointer
        // buuferIndex, which buffer pointer is currently at
        private int ways, lookahead, bufferIndex;
        private InputStream stream;
        private int[][] buffers;
        
        public Peeker(InputStream stream) throws IOException { this(2, stream); }
        public Peeker(int ways, InputStream stream) throws IOException {
            this.ways = Math.max(2, ways);
            this.stream = stream;
            this.bufferIndex = this.lookahead = 0;
            this.buffers = new int[this.ways][BUFFER_SIZE+1];
            for (int i = 0; i < this.ways; i++) this.buffers[i][BUFFER_SIZE] = EOF;
            // load the first buffer
            load(0);
        }
        
        public boolean hasNext() { return buffers[bufferIndex][lookahead] != EOF; }
        
        public char nextChar() throws IOException, LexicalException {
            if (!hasNext()) return EOF_CHAR;
            int c = advance();
            return (c == EOF) ? EOF_CHAR : (char) c;
        }
        
        public int advance() throws IOException {
            // reach the end of current buffer
            if (buffers[bufferIndex %= ways][lookahead] == EOB) {
                    // load the next buffer
                    load(bufferIndex = (bufferIndex + 1) % ways);
                    // reset lookahead pointer
                    lookahead = 0;
            }
            // return current lookahead character and move lookahead pointer forward
            return buffers[bufferIndex][lookahead++];
        }
        
        private void load(int index) throws IOException {
            index %= this.ways;
            // clear buffer to be loaded
            clear(index);
            int i = 0;
            while (i < BUFFER_SIZE) {
                this.buffers[index][i] = this.stream.read();
                if (this.buffers[index][i] == EOF) break;
                i++;
            }
            // set sentinel
            this.buffers[index][Math.min(i + 1, BUFFER_SIZE)] = EOF;
            this.buffers[index][BUFFER_SIZE] = EOB;
        }
        
        public void clear(int index) {
            for (int i = 0; i < BUFFER_SIZE; i++) this.buffers[index][i] = 0;
            // set sentinel
            buffers[index][BUFFER_SIZE] = EOB;
        }
    }
    
    /**
     * Read from user input stream
     * @throws IOException
     */
    public Lexer() throws IOException { this(System.in); }

    /**
     * Read from a file
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Lexer(String filename) throws FileNotFoundException, IOException {
        this(new FileInputStream(filename));
        this.filename = filename;
    }
    
    /**
     * Read from a stream
     * @param stream
     * @throws IOException
     */
    public Lexer(InputStream stream) throws IOException {
        this.stream = stream;
        this.pushback = new Stack<>();
        this.lexemeBuilder = new StringBuilder();
        this.peeker = new Peeker(this.stream);
        this.peek = ' ';
        this.lastToken = null;
    }
    
    /**
     * Indicate if there is next token
     * @return boolean indicating if there is next token
     */
    public boolean hasNextToken() {
        return lastToken == null || !lastToken.getType().equals(TokenType.ENDOFFILE);
    }
    
    /**
     * Return the next token
     * @return Token
     * @throws IOException
     * @throws LexicalException
     */
    public Token getNextToken() throws IOException, LexicalException {
        // skip blank, tab, comment, block comment
        skip();
        // at this point, this.peek holds a value that can't be skipped
        // reset lexeme buffer
        reset();
        
        // End of file
        if (peek == EOF_CHAR) return lastToken = new Token(TokenType.ENDOFFILE, null);
        if (!Lexer.isValidInput(peek)) throw new InvalidInputException(line, filename, peek);
        
//        // Num case with sign
//        if (peek == '+' || peek == '-') {
//            char prev = peek;
//            peek = nextChar();
//            // if the sign is followed by a digit and previous token can't be combined with
//            // the constant followed
//            if (Character.isDigit(peek) && !isComputable(lastToken)) {
//                // append the sign
//                lexemeBuilder.append(prev);
//                // read the number constant
//                return lastToken = readNum();
//            }
//            // recover lookahead
//            pushback.push(peek);
//            peek = prev;
//        }

        // Num case
        if (Character.isDigit(peek)) {
            return lastToken = readNum();
        }
        
        // Word case
        if (Character.isLetter(peek)) {
            return lastToken = readWord();
        } 
        
        // Op case
        return lastToken = readOp();
    }
    
    /**
     * Return current line number
     * @return line number
     */
    public int getLine() { return line; }
    
    /**
     * Return read file's filename
     * @return filename
     */
    public String getFilename() { return filename; }
    
    private Token readNum() throws IOException, LexicalException {
        // start with the first digit
        lexemeBuilder.append(peek);
        int state = 1;
        // simulates REGEX: digits(.digits)?([Ee][+-]?digits)? 
        // accept states are 1, 2, 4, and 5
        do {
            peek = nextChar();
            switch(state) {
                // start state, we have the first digit or sign with digit
                // e.g -1, 1
                case 1:
                    if (Character.isDigit(peek)) {
                        state = 1;
                        lexemeBuilder.append(peek);
                    } else if (Character.isLetter(peek)) {
                        lexemeBuilder.append(peek);
                        if (peek != 'e' && peek != 'E') {
                            throw new IllFormatedConstantException(line, filename, lexemeBuilder.toString());
                        } else {
                            state = 3;
                        }
                    } else if (peek == '.') {
                        state = 2;
                    // see other character, and since current state is accept state
                    // return token
                    } else {
                        return new Token(TokenType.INTCONSTANT, Integer.parseInt(lexemeBuilder.toString()));
                    }
                    break;
                // see the first dot
                case 2:
                    if (Character.isDigit(peek)) {
                        state = 4;
                        lexemeBuilder.append('.').append(peek);
                    // see the second dot, and since current state is accept state
                    // return token
                    } else if (peek == '.') {
                        // recover lookahead
                        pushback.push('.');
                        return new Token(TokenType.INTCONSTANT, Integer.parseInt(lexemeBuilder.toString()));
                    // must follow a digit, reject here
                    } else {
                        lexemeBuilder.append(peek);
                        throw new IllFormatedConstantException(line, filename, lexemeBuilder.toString());
                    }
                    break;
                // see the first e
                case 3:
                    // must follow a digit
                    if (Character.isDigit(peek)) {
                        state = 5;
                        lexemeBuilder.append(peek);
                    } else if (peek == '+' || peek == '-') {
                        state = 6;
                        lexemeBuilder.append(peek);
                    // see other character, and reject
                    } else {
                        lexemeBuilder.append(peek);
                        throw new IllFormatedConstantException(line, filename, lexemeBuilder.toString());
                    }
                    break;
                // in format of 7.313
                case 4:
                    if (Character.isDigit(peek)) {
                        state = 4;
                        lexemeBuilder.append(peek);
                    } else if (Character.isLetter(peek)) {
                        lexemeBuilder.append(peek);
                        if (peek != 'e' && peek != 'E') {
                            throw new IllFormatedConstantException(line, filename, lexemeBuilder.toString());
                        } else {
                            state = 3;
                        }
                    // see other character, and since current state is accept state
                    // return token
                    } else {
                        return new Token(TokenType.REALCONSTANT, Double.parseDouble(lexemeBuilder.toString()));
                    }
                    break;
                // in format of 1.242e10 or 1e10
                case 5:
                    if (Character.isDigit(peek)) {
                        state = 5;
                        lexemeBuilder.append(peek);
                    } else if (Character.isLetter(peek)) {
                        throw new IllFormatedConstantException(line, filename, lexemeBuilder.toString());
                    // see other character, and since current state is accept state
                    // return token
                    } else {
                        return new Token(TokenType.REALCONSTANT, Double.parseDouble(lexemeBuilder.toString()));
                    }
                    break;
                // in format of 1.242e-10 or 1e-10
                case 6:
                    if (Character.isDigit(peek)) {
                        state = 5;
                        lexemeBuilder.append(peek);
                    // must follow digits, reject
                    } else {
                        lexemeBuilder.append(peek);
                        throw new IllFormatedConstantException(line, filename, lexemeBuilder.toString());
                    }
            }
        } while (true);
    }
    
    private Token readWord() throws IOException, LexicalException {
        // starts with first letter
        lexemeBuilder.append(peek);
        do {
            peek = nextChar();
            // limit length
            if (lexemeBuilder.length() > MAX_IDENTIFIER_SIZE) {
                throw new IdentifierTooLongException(line, filename, lexemeBuilder.toString(), Lexer.MAX_IDENTIFIER_SIZE);
            }
            // greedily append letters
            if (Character.isLetterOrDigit(peek)) {
                lexemeBuilder.append(peek);
            // meet delimiters
            } else {
                String id = lexemeBuilder.toString();
                TokenType tag = TokenType.IDENTIFIER;
                String value = id;
                // see if is from reserved word list
                if (ReservedWords.isReservedWord(id)) {
                    tag = ReservedWords.mapReservedWordType(id);
                    value = null;
                }
                // see if is from operation list, e.g div
                if (ReservedWords.isReservedOp(id)) {
                    tag = ReservedWords.mapReservedOpType(id);
                    Integer v;
                    switch(id.toLowerCase()) {
                        case "or":
                        case "div":
                            v = 3;
                            break;
                        case "mod":
                            v = 4;
                            break;
                        default:
                            v = 5;
                    }
                    return new Token(tag, v);
                }
                return new Token(tag, value);
            }
        } while (true);
    }
    
    private Token readOp() throws IOException, LexicalException {
        // record two lookahead
        char prev = peek;
        peek = nextChar();
        lexemeBuilder.append(prev);
        
        // check first lookahead
        switch (prev) {
            case '+':
                // uniary case
                if (!isComputable(lastToken)) return new Token(TokenType.UNARYPLUS, null);
                // left case is addop
                return new Token(TokenType.ADDOP, 1);
            case '-':
                // uniary case
                if (!isComputable(lastToken)) return new Token(TokenType.UNARYMINUS, null);
                // left case is addop
                return new Token(TokenType.ADDOP, 2);
            case '.':
                if (peek == prev) {
                    lexemeBuilder.append(peek);
                    // move forward as we've used up both lookaheads
                    peek = nextChar();
                    return new Token(TokenType.DOUBLEDOT, null);
                } else {
                    return new Token(TokenType.ENDMARKER, null);
                }
            case '*':
                return new Token(TokenType.MULOP, 1);
            case '/':
                return new Token(TokenType.MULOP, 2);
            case '=':
                return new Token(TokenType.RELOP, 1);
            case '>':
                if (peek == '=') {
                    lexemeBuilder.append(peek);
                    // move forward as we've used up both lookaheads
                    peek = nextChar();
                    return new Token(TokenType.RELOP, 6);
                } else {
                    return new Token(TokenType.RELOP, 4);
                }
            case '<':
                switch (peek) {
                    case '=':
                        lexemeBuilder.append(peek);
                        // move forward as we've used up both lookaheads
                        peek = nextChar();
                        return new Token(TokenType.RELOP, 5);
                    case '>':
                        lexemeBuilder.append(peek);
                        /// move forward as we've used up both lookaheads
                        peek = nextChar();
                        return new Token(TokenType.RELOP, 2);
                    default:
                        return new Token(TokenType.RELOP, 3);
                }
            case ':':
                if (peek == '=') {
                    lexemeBuilder.append(peek);
                    // move forward as we've used up both lookaheads
                    peek = nextChar();
                    return new Token(TokenType.ASSIGNOP, null);
                } else {
                    return new Token(TokenType.COLON, null);
                }
            default:
                // try to map the first lookahead to a token type
                TokenType tag = ReservedWords.RESERVED_PUNC_TOKEN_MAP.get(Character.toString(prev));
                // if fails, meaning the character is not valid
                if (tag == null) {
                    throw new InvalidInputException(line, filename, prev);
                } else {
                    return new Token(tag, null);
                }
        }
    }
    
    private char nextChar() throws IOException, LexicalException {
        if (!pushback.isEmpty()) return pushback.pop();
        return peeker.nextChar();
    }
    
    private boolean isComputable(Token t) {
        if (t == null) return false;
        switch(t.getType()) {
            case IDENTIFIER:
            case INTCONSTANT:
            case REALCONSTANT:
            case RIGHTPAREN:
            case RIGHTBRACKET:
                return true;
            default:
                return false;
        }
    }
    
    public String getLastLexeme() {
        return lastLexeme;
    }
    
    public String getCurrentLexeme() {
        return lexemeBuilder.toString();
    }
    
    private void reset() {
        lastLexeme = lexemeBuilder.toString();
        lexemeBuilder.delete(0, lexemeBuilder.length());
    }
    /*
    This function assumes that this.peek is already filled through nextChar().
    So will only update this.peek at the end of loop (if we need keep reading)
    */
    @SuppressWarnings("empty-statement")
    private void skip() throws IOException, LexicalException {
        boolean keepReading = true;
        while (keepReading && peek != EOF_CHAR) {
            switch(peek) {
                // ignore " " and tab
                case ' ': 
                case '\r':
                case '\t':
                    break;
                // count line number
                case '\n':
                    lastToken = null;
                    this.line++;
                    break;
                // skip comment embraced by {} 
                case '{':
                    while ((peek = nextChar()) != EOF_CHAR && peek != '}') {
                        if (peek == '\n') {
                            this.line++;
                        }
                    }
                    // no } matched for {
                    if (peek == EOF_CHAR) throw new IllFormatedCommentException(line, filename);
                    break;
                case '}':
                    throw new IllFormatedCommentException(line, filename);
                default:
                    // if in comment block, ignore
                    // else end loop
                    keepReading = false;
            }
            // if keep reading
            if (keepReading) peek = nextChar();
        }
    }
    
    /**
     * Unit test
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String filename = "testcase0.txt";
        if (args.length > 0) {
            filename = args[0];
        }
        Lexer lexer = new Lexer(filename);
//        Lexer lexer = new Lexer("errorcase.txt");
        try {
            while (lexer.hasNextToken()) {
                System.out.println(lexer.getNextToken());
            }
            System.out.println("\n****************************************");
            System.out.println("Total lines: " + lexer.line);
        } catch (LexicalException ex) {
            Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
