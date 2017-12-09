/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author Gavin
 */
public enum SemanticAction implements GrammarSymbol {
    action1(1), action2(2), action3(3), action4(4), action5(5), action6(6),
    action7(7), action9(9), action11(11), action13(13), action15(15),
    action16(16), action17(17), action19(19), action20(20), action21(21),
    action22(22), action24(24), action25(25), action26(26), action27(27),
    action28(28), action29(29), action30(30), action31(31), action32(32),
    action33(33), action34(34), action35(35), action36(36), action37(37),
    action38(38), action39(39), action40(40), action41(41), action42(42),
    action43(43), action44(44), action45(45), action46(46), action47(47),
    action48(48), action49(49), action50(50), action51(51), action52(52),
    action53(53), action54(54), action55(55), action56(56), action57(57),
    action58(58);

    private int n;

    private SemanticAction(int i) {
        n = i;
    }

    public int getIndex() {
        return n;
    }

    public boolean isToken() {
        return false;
    }

    public boolean isNonTerminal() {
        return false;
    }

    public boolean isAction() {
        return true;
    }

    public boolean equals(GrammarSymbol other) {
        return other.isAction() && this.n == other.getIndex();
    }
    
    public String toString() {
        return "<" + name() + ">";
    }
}
