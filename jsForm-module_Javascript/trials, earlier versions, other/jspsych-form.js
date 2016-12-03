/*

Dependency: jsPsych, jQuery, Material Design Lite

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

*/


jsPsych.plugins["form"] = (function() {

  var plugin = {};


  plugin.trial = function(display_element, trial) {

    // set default values for parameters
    trial.parameter = trial.parameter || 'default value';

    trial = jsPsych.pluginAPI.evaluateFunctionParameters(trial);

    // track unique ids for each element
    var __INPUT_CHECKBOX = 0;
    var __INPUT_DATE = 0;
    var __INPUT_DATETIME = 0;
    var __INPUT_DATETIME_LOCAL = 0;
    var __INPUT_EMAIL = 0;
    var __INPUT_FILE = 0;
    var __INPUT_MONTH = 0;
    var __INPUT_NUMBER = 0;
    var __INPUT_PASSWORD = 0;
    var __INPUT_RADIO = 0;
    var __INPUT_RANGE = 0;
    var __INPUT_SEARCH = 0;
    var __INPUT_TEL = 0;
    var __INPUT_TEXT = 0;
    var __INPUT_TIME = 0;
    var __INPUT_URL = 0;
    var __INPUT_WEEK = 0;

    var __TOGGLE_GROUP = 0;

    var __BUTTON = 0;
    var __SELECT = 0;
    var __OPTION = 0;
    var __TEXTAREA = 0;

    var __FORM = 0;

    // Help functions
    if (!String.prototype.format) {
      String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
          return typeof args[number] != 'undefined' ? args[number] : match;
        });
      };
    }

    if (!String.prototype.startsWith) {
      String.prototype.startsWith = function(prefix) {
        return this.slice(0, prefix.length) === prefix;
      };
    }

    if (!String.prototype.endsWith) {
      String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
      };
    }

    if (!String.prototype.capitalize) {
      String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
      }
    }

    function inherit(proto) {
      function F() {}
      F.prototype = proto;
      return new F;
    }

    /*
    e.g.

    var schema = {
        form: {form_title: "Test #1", ribbon_bg: "somePicture", form_description: ""},
      
      // for more avaliable settings, check the attributes of classes for each type
        "Question #1": {type: "short answer", label: ""}, 
        "Question #2": {type: "password"},
        "Question #3": {type: "checkbox", labels: ["option1", "option2"], images:[image1, image2], values:[1, 2, 3, 4]},
        "Question #4": {type: "radio", labels: ["option1", "option2"]}, // will automatically fill valuse
        "Question #5": {type: "range"},

        // not display question
        "Question #6": {type: "dropdown", needQuestion: false},
      
      // insert paragraph (similar for form)
        "Question #7": {type: "long answer", question_description: ""}, // better styled
        "Question #8<p>Contents</p>": {type: "long answer"}, 

        onSubmit: {label: "Next"}
      };


      */
      function createForm(display_element, opt) {
        opt.form = opt.form || {};
        var form = new Form(display_element, opt.form);
        var form_id = form.id;

        var tags = [];
        tags.push(form);
        for (var i in Object.keys(opt)) {
          i = Object.keys(opt)[i]
          if (i == "form" || i == "onSubmit")
            continue;
          item = opt[i]
          item.question = item.question || i;
          var type = item.type;
          var tag;
          switch(type) {
      // major uses
      case "short answer":
      item.type = "text";
      case "text":
      tag = new InputText(form_id, item);
      break;
      case "long answer":
      item.type = "textarea";
      case "textarea":
      tag = new Textarea(form_id, item);
      break;
      case "dropdown":
      tag = new Dropdown(form_id, item);
      break;
      case "checkbox":
      case "switch":
      case "radio":
      tag = new ToggleGroup(form_id, item);
      break;
      case "range":
      tag = new Range(form_id, item);
      break;
      // minor features
      case "date":
      tag = new InputDate(form_id, item);
      break;
      case "datetime":
      tag = new InputDatetime(form_id, item);
      break;
      case "datetime-local":
      tag = new InputDatetimeLocal(form_id, item);
      break;
      case "email":
      tag = new InputEmail(form_id, item);
      break;
      case "file":
      tag = new UploadFile(form_id, item);
      break;
      case "month":
      tag = new InputMonth(form_id, item);
      break;
      case "password":
      tag = new InputPassword(form_id, item);
      break;
      case "search":
      tag = new InputSearch(form_id, item);
      break;
      case "telephone":
      tag = new InputTel(form_id, item);
      break;
      case "time":
      tag = new InputTime(form_id, item);
      break;
      case "url":
      tag = new InputUrl(form_id, item);
      break;
      case "week":
      tag = new InputWeek(form_id, item);
      break;
    }
    tags.push(tag);
  }

  var button = new Button(form_id, opt.onSubmit);
  tags.push(button);

  return tags;
}


/*
############################################################
# Form
# Form does the following: render a MDL style form 

#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id <-- automatically assigned
#
# @param display_element
# @param item --> a set of values for setting

# @param item.form_title
# @param item.form_title_size
# @param item.form_title_color
# @param item.form_description
# @param item.form_description_color 
# @param item.form_description_size 

# layout settings:
# @param item.layout_color 
# @param item.ribbon_color 
# @param item.ribbon_height 
# @param item.ribbon_bg
# @param item.ribbon_bg_size
# @param item.content_bg_color
# @param item.content_text_color
############################################################
# @return
#
############################################################
*/
function Form(display_element, item = {}) {
  this.type = "form";
  this.display_element = display_element || "body";
  this.id = item.id || "{0}_{1}".format(this.type, __FORM++);

  this.layout_color = item.layout_color || "grey-300";

  this.ribbon_color = item.ribbon_color || '#3F51B5';
  this.ribbon_height = item.ribbon_height || '40vh';
  this.ribbon_bg = (item.ribbon_bg) ? "background: url({0});".format(item.ribbon_bg) : "";
  this.ribbon_bg_size = item.ribbon_bg_size || "background-size: contain cover;";

  this.ribbon = '<div style="height: {0};-webkit-flex-shrink: 0;-ms-flex-negative: 0;flex-shrink: 0;background-color: {1};{2}{3}"></div>'.format(
    this.ribbon_height, this.ribbon_color, this.ribbon_bg, this.ribbon_bg_size);

  this.content_bg_color = item.content_bg_color || "grey-100";
  this.context_text_color = item.context_text_color || "black-800";
  this.form_title = item.form_title || "Untitled Form";

  this.form_title_size = item.form_title_size || "40px";
  this.form_title_color = item.form_title_color || "black-800";

  this.form_description = item.form_description || "";
  this.form_description_size = item.form_description_size || "14px";
  this.form_description_color = item.form_description_color || "grey-600";
  if (this.form_description)
    this.form_description = '<p style="font-size: {0};padding-top: 20px;" class="mdl-layout-title mdl-color-text--{1}">{2}</p>'.format(this.form_description_size, this.form_description_color, this.form_description);

  this.form_title = '<label style="font-size: {0};padding-bottom: 30px; font-weight: bolder;" class="mdl-layout-title mdl-color-text--{1}">{2}<br>{3}</label>'.format(
    this.form_title_size, this.form_title_color, this.form_title, this.form_description)

  this.content = '<main class="mdl-layout__content" style="margin-top: -35vh;-webkit-flex-shrink: 0;-ms-flex-negative: 0;flex-shrink: 0;">\
  <div class="mdl-grid" style="max-width: 1600px;width: calc(100% - 16px);margin: 0 auto;margin-top: 10vh;">\
  <div class="mdl-cell mdl-cell--2-col mdl-cell--hide-tablet mdl-cell--hide-phone"></div>\
  <div class="mdl-color--{0} mdl-shadow--4dp mdl-cell mdl-cell--8-col" style="border-radius: 2px;padding: 80px 56px;margin-bottom: 80px;">\
  <div class="mdl-color-text--{1}" id="{2}" style="width: 512px;">{3}</div></div>\
  <div class="mdl-layout mdl-color-text--grey-600" style="text-align: center; font-size: 12px; margin-top: -60px">\
  This content is neither created nor endorsed by jsPsych.\
  </div><div class="mdl-layout mdl-color-text--grey-700" style="text-align: center; font-size: 19px; margin-top: -30px">\
  jsPsych Forms</div></div></main>'.format(
    this.content_bg_color, this.context_text_color, this.id, this.form_title);


  this.html = '<form><div class="mdl-layout mdl-layout--fixed-header mdl-js-layout mdl-color--{0}">{1}{2}</div></form>'.format(
    this.layout_color, this.ribbon, this.content);

  this.render();
}
Form.prototype.render = function() {
  $(this.display_element).html(this.html);
}

/*
############################################################
# Tag does the following:
#
# @param parent_id --> the id of its parent element
# @param item --> an object of values for setting

# @param item.label --> general inner html content
# @param item.question --> question
# @param item.question_description --> question description
# @param item.question_color --> color of of question
# @param item.question_size --> font size of question
# @param item.question_description_size --> font size of question description
# @param item.question_description_color --> color of question description
# @param item.needQuestion --> whether displaying question
# @param item.newline --> whether inserting new line when rendering

# @param item.value --> html attribute
# @param item.name --> html attribute
# @param item.type --> html attribute
# @param item.id --> html attribute
# @param item.disabled --> html attribute
# @param item.maxlength --> html attribute
# @param item.readonly --> html attribute
# @param item.required --> html attribute
# @param item.autofocus --> html attribute
# @param item.size --> html attribute

############################################################
# @return
#
############################################################
*/
function Tag(parent_id, item) {
  // standard attributs
  this.parent_id = parent_id;

  this.label = item.label || "";
  this.value = item.value || "";
  this.name = item.name || "";
  this.type = item.type || "";
  this.id = item.id || "";
  this.name = item.name || this.id;
  this.question_color = item.question_color || "black-800";
  this.question = "";

  this.question_description = item.question_description || "";
  this.question_description_size = item.question_description_size || "14px";
  this.question_description_color = item.question_description_color || "grey-600";
  if (this.question_description)
    this.question_description = '<p style="font-size: {0};padding-top: 20px;" class="mdl-layout-title mdl-color-text--{1}">{2}</p>'.format(
      this.question_description_size, this.question_description_color, this.question_description
      );

  if (item.needQuestion) {
    this.question = item.question || "Untitled Question";
    this.question = '<label class="mdl-layout-title mdl-color-text--{0}" style="font-weight: bold;" >{1}</label>'.format(
      this.question_color, this.question
      );
  }

  //default settings
  this.newline = item.newline || false;
  this.disabled = (item.disabled) ? 'disabled="disabled"' : "";
  this.maxlength = item.maxlength || "";
  this.readonly = (item.readonly) ? 'readonly="readonly"' : "";
  this.required = (item.required) ? 'required="required"' : "";
  this.autofocus = (item.autofocus) ? 'autofocus="autofocus"' : "";
  this.size = item.size || "";

  this.html = "";
}
Tag.prototype = {
  render: function() {
    if (this.newline)
      this.html = "<br>" + this.html;
    $("#{0}".format(this.parent_id)).append(this.question + this.question_description + this.html);
  }
}

/*
############################################################
# Button **inherits** Tag
# Button does the following: render a MDL style button
#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- False
#   
# @param parent_id --> the id of its parent element
# @param item --> an object of values for setting
# @param item.buttonStyle --> see MDL attribute
# @param item.color --> see MDL attribute
# @param item.primary --> see MDL attribute
# @param item.accent --> see MDL attribute
# @param item.ripple --> see MDL attribute
# @param item.icon --> see MDL attribute
# @param item.onclick --> html attribute
############################################################
# @return
#
############################################################
*/
function Button(parent_id, item = {}) {
  item.id = item.id || "{0}_{1}".format(item.type, __BUTTON++);
  item.type = item.type || "button";
  Tag.call(this, parent_id, item);

  //MDL style
  this.addon = ""
  this.buttonStyle = item.buttonStyle || "raise";
  switch (this.buttonStyle) {
    case "raise":
    this.addon += " mdl-button--raised";
    break;
    case "fab":
    this.addon += " mdl-button--fab";
    break;
    case "minifab":
    this.addon += " mdl-button--mini-fab";
    break;
    case "icon":
    this.addon += " mdl-button--icon";
    break;
  }

  this.color = (item.color == false) ? false : true;
  this.addon += ((this.color) ? " mdl-button--colored" : "");
  this.primary = item.primary || false;
  this.addon += ((this.primary) ? " mdl-button--primary" : "");
  this.accent = item.accent || false;
  this.addon += ((this.accent) ? " mdl-button--accent" : "");
  this.ripple = (item.ripple == false) ? false : true;
  this.addon += ((this.ripple) ? " mdl-js-ripple-effect" : "");

  this.style = "mdl-button mdl-js-button" + this.addon;

  this.label = item.label || "Button";
  this.value = item.value || "";
  this.onclick = item.onclick || "";


  this.icon = item.icon || "";
  if (this.icon != "")
    this.icon = '<i class="material-icons">{0}</i>'.format(this.icon);

  this.html = '<div><button class="{0}" id="{1}" type="{2}" value="{3}" {4} form="{5}" onclick="{6}" name="{9}">{7}{8}</button></div>'.format(
    this.style, this.id, this.type, this.value, this.disabled, this.parent_id, this.onclick, this.icon, this.label, this.name);

  this.render();
}
Button.prototype = inherit(Tag.prototype);

/*
############################################################
# UploadFile **inherits** Tag
# UploadFile does the following:
#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- False
#
# @param parent_id --> the id of its parent element
# @param item --> an object of values for setting
# @param item.fileType --> prompts users to upload certain file type
# @param item.icon --> see MDL attribute
############################################################
# @return
#
############################################################
*/
function UploadFile(parent_id, item = {}) {
  item.type = "file";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_FILE++);
  Tag.call(this, parent_id, item);

  this.label_id = "label_{0}".format(this.id);
  this.fileType = item.fileType || "Upload a file from here";
  this.icon = item.icon || "cloud_upload";
  this._style = 'style="position:absolute;top: 0;right: 0;width: 300px;height: 100%;z-index: 4;cursor: pointer;opacity: 0"';

  this.html = this._generate();
  this.render();

  var label_id = this.label_id;
  $("#{0}".format(this.id)).change(function() {
    $("#{0}".format(label_id)).val(this.files[0].name);
    $("#{0}".format(label_id)).text(this.files[0].name);
  });
};
UploadFile.prototype = inherit(Tag.prototype);
UploadFile.prototype._generate = function() {
  var html = '<div class="mdl-textfield mdl-js-textfield" style="box-sizing: border-box;">\
  <input id="{3}" class="mdl-textfield__input" readonly placeholder="{4}">\
  <label class="mdl-textfield__label" for="{3}"></label></div>\
  <div class="mdl-button mdl-js-button mdl-button--primary mdl-button--icon" style="right: 0;">\
  <i class="material-icons">{0}</i>\
  <input type="file" id="{1}" {2} {3} {4} {5} {6} style="padding-botton: 36px"></div>'.format(
    this.icon, this.id, this._style, this.label_id, this.fileType,
    this.required, this.readonly, this.disabled, this.autofocus
    )

  return html
}

/*
############################################################
# Range **inherits** Tag
# Range does the following:
#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- True or assigned by user
#
# @param parent_id --> the id of its parent element
# @param item --> an object of values for setting
# @param item.width --> html attribute
# @param item.max --> html attribute
# @param item.min --> html attribute
# @param item.step --> html attribute
# @param item.value_prompt --> prompts users the current value
############################################################
# @return
#
############################################################
*/
function Range(parent_id, item = {}) {
  item.type = "range";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_RANGE++);
  item.value = item.value || "0";
  item.needQuestion = (item.needQuestion == false) ? false : true;
  Tag.call(this, parent_id, item);

  this.label_id = "label_{0}".format(this.id);
  this.width = item.width || "300px";
  this.max = item.max || 100;
  this.min = item.min || 0;
  this.step = item.step || 1;
  this.value_prompt = item.value_prompt || "value: ";
  this.value_label = '<label class="mdl-color-text--grey-700" id="{0}" readonly>{1}</label>'.format(
    this.label_id, this.value
    );

  this.html = '<div class="mdl-textfield mdl-js-textfield" style="box-sizing: border-box;">{12} {6}\
  <div style="width:{0};"><input class="mdl-slider mdl-js-slider" type="range" form="{7}"\
  id="{1}" min="{2}" max="{3}" value="{4}" step="{5}" {8} {9} {10} {11}></div></div>'.format(
    this.width, this.id, this.min, this.max, this.value,
    this.step, this.value_label, this.parent_id, this.required,
    this.readonly, this.autofocus, this.disabled, this.value_prompt
    );

  this.render();

  var label_id = this.label_id;
  var id = this.id;
  $("#" + this.id).change(function() {;
    $("#" + label_id).text($("#" + id).val());
  })
}
Range.prototype = inherit(Tag.prototype);

/*
############################################################
# Dropdown **inherits** Tag
# Dropdown does the following:
#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- True or assigned by user
#
# @param parent_id --> the id of its parent element
# @param item --> an object of values for setting
# @param item.options --> the labels of the options
# @param item.option_values --> the values of the options
# @param item.dropRight --> define from which direction the list drops down, default is "false"
# @param item.choose_prompt --> prompts the users to choose
############################################################
# @return
#
############################################################
*/
function Dropdown(parent_id, item={}) {
  item.type = "select";
  item.id = item.id || "{0}_{1}".format(item.type, __SELECT++);
  item.needQuestion = (item.needQuestion == false) ? false : true;
  Tag.call(this, parent_id, item);

  this.options = item.options || ["option1", "option2", "option3"];
  this.option_ids = [];
  this.option_values = [];
  for (var i in this.options) {
    this.option_ids.push("option_{0}".format(__OPTION++));
    this.option_values.push(this.options[i]);
  }

  this.value = item.value || "";
  this.label_id = "label_{0}".format(this.id);
  this.button_id = "button_{0}".format(this.id);
  this.dropRight = item.dropRight || false;
  this.choose_prompt = item.choose_prompt || "Choose";
  this._style = "mdl-menu mdl-js-menu mdl-js-ripple-effect" + ((this.dropRight) ? "mdl-menu--bottom-right" : "mdl-menu--bottom-left");

  this.html = this.question + '<div class="mdl-textfield mdl-js-textfield">\
  <input id="{4}" form="{8}" class="mdl-textfield__input" readonly {5} {6} value="{7}"><button id="{0}" form="{8}" style="right: 0;"\
  class="mdl-button mdl-js-button mdl-button--icon">\
  <i class="material-icons">expand_more</i></button>\
  <ul id="{1}" class="{2}" for="{0}" form="{8}">{3}</ui></div>'.format(
    this.button_id, this.id, this._style, this._option_factory(), 
    this.label_id, this.required, this.disabled, this.value, this.parent_id
    );

  this.render();
  var option_values = this.option_values;
  var label_id = this.label_id;
  for (let i in this.option_ids) {

    $("#"+this.option_ids[i]).click(function() {
      $("#"+label_id).val(option_values[i]);
    })
  } 
}
Dropdown.prototype = inherit(Tag.prototype);
Dropdown.prototype._option_factory = function () {
  var html = '<li disabled class="mdl-menu__item mdl-menu__item--full-bleed-divider">{0}</li>'.format(this.choose_prompt);
  
  for (var i in this.options) {
    html += '<li id="{1}" value="{2}" class="mdl-menu__item">{0}</li>'.format(
      this.options[i], this.option_ids[i], this.option_values[i]
      );
  }

  return html;
}

/*
############################################################
# InputTextField **inherits** Tag
# InputTextField does the following: as a superclass for <input> tag

#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- True or assigned by user
#
# @param parent_id --> the id of its parent element
# @param item --> a set of values for setting
# @param item.expandable --> see MDL attribute
# @param item.floating --> see MDL attribute
# @param item.icon --> see MDL attribute
# @param item.errorInfo --> see MDL attribute, define desired error message, the default is "Your input is not as required!"
# @param item.pattern --> html attribute
# @param item.accessKey --> html attribute
# @param item.defaultValue --> html attribute
# @param item.alt --> html attribute
# @param item.tabIndex --> html attribute
############################################################
# @return
#
############################################################
*/
function InputTextField(parent_id, item) {
  item.needQuestion = (item.needQuestion == false) ? false : true;
  Tag.call(this, parent_id, item);

  //MDL style
  this.expandable = item.expandable || false;
  this.expandable = (this.expandable) ? " mdl-textfield--expandable" : "";
  this.floating = item.floating || false;
  this.floating = (this.floating) ? " mdl-textfield--floating-label" : "";
  this.style = "mdl-textfield mdl-js-textfield{0}{1}".format(this.expandable, this.floating);

  this.icon = item.icon || "";
  this.pattern = item.pattern || ".*";
  this.errorInfo = item.errorInfo || "Your input is not as required!";

  //attributes
  this.accessKey = item.accessKey || "";
  this.defaultValue = item.defaultValue || "";
  this.alt = item.alt || "";
  this.tabIndex = item.tabIndex || "";
}
InputTextField.prototype = inherit(Tag.prototype);
InputTextField.prototype._generate = function() {
  var component = '<input class="mdl-textfield__input" type="{0}" id="{1}" maxlength="{2}" size="{3}"\
  {4} {5} pattern="{6}" {7} {8} {9} {10} {11} {12} value="{13}" form="{14}">'.format(
    this.type, this.id, this.maxlength, this.size,
    this.required, this.readonly, this.pattern,
    this.disabled, this.autofocus, this.accessKey,
    this.alt, this.defaultValue, this.tabIndex,
    this.value, this.parent_id) +
  '<label class="mdl-textfield__label" for="{0}">{1}</label>'.format(this.id, this.label) +
  '<span class="mdl-textfield__error">{0}</span>'.format(this.errorInfo);

  if (this.expandable != "")
    component = '<div class="mdl-textfield__expandable-holder">' + component + '</div>';

  if (this.icon != "")
    component = '<label class="mdl-button mdl-js-button mdl-button--icon" for="{0}"><i class="material-icons">{1}</i></label>'.format(this.id, this.icon) + component;

  return '<div class="{0}">{1}<div>'.format(this.style, component);
};

function InputDate(parent_id, item = {}) {
  item.type = "date";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_DATE++);
  InputTextField.call(this, parent_id, item);

  this.html = this._generate();
  this.render();
}
InputDate.prototype = inherit(InputTextField.prototype);

function InputDatetime(parent_id, item = {}) {
  item.type = "datetime";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_DATETIME++);
  InputTextField.call(this, parent_id, item);

  this.html = this._generate();
  this.render();
}
InputDatetime.prototype = inherit(InputTextField.prototype);

function InputDatetimeLocal(parent_id, item = {}) {
  item.type = "datetime-local";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_DATETIME_LOCAL++);
  InputTextField.call(this, parent_id, item);

  this.html = this._generate();
  this.render();
}
InputDatetimeLocal.prototype = inherit(InputTextField.prototype);

function InputEmail(parent_id, item = {}) {
  item.type = "email";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_EMAIL++);
  InputTextField.call(this, parent_id, item);

  this.label = item.label || "Please enter you email...";

  this.html = this._generate();
  this.render();
}
InputEmail.prototype = inherit(InputTextField.prototype);


function InputMonth(parent_id, item = {}) {
  item.type = "month";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_MONTH++);
  InputTextField.call(this, parent_id, item);

  this.html = this._generate();
  this.render();
}
InputMonth.prototype = inherit(InputTextField.prototype);

function InputNumber(parent_id, item = {}) {
  item.type = "number";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_NUMBER++);
  InputTextField.call(this, parent_id, item);

  this.label = item.label || "Please enter a number...";

  this.html = this._generate();
  this.render();
}
InputNumber.prototype = inherit(InputTextField.prototype);

function InputPassword(parent_id, item = {}) {
  item.type = "password";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_PASSWORD++);
  item.needQuestion = item.needQuestion || false;
  item.floating = (item.floating == false) ? false : true;
  InputTextField.call(this, parent_id, item);

  this.label = item.label || "Please enter your password...";

  this.html = this._generate();
  this.render();
}
InputPassword.prototype = inherit(InputTextField.prototype);

function InputSearch(parent_id, item = {}) {
  item.type = "search";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_SEARCH++);
  item.expandable = (item.expandable == false) ? false : true;
  item.floating = (item.expandable) ? false : true;
  item.icon = item.icon || "search";
  item.needQuestion = false;
  InputTextField.call(this, parent_id, item);

  this.html = this._generate();
  this.render();
}
InputSearch.prototype = inherit(InputTextField.prototype);

function InputTel(parent_id, item = {}) {
  item.type = "tel";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_TEL++);
  InputTextField.call(this, parent_id, item);

  this.label = item.label || "Please enter your telephone number...";

  this.html = this._generate();
  this.render();
}
InputTel.prototype = inherit(InputTextField.prototype);

function InputText(parent_id, item = {}) {
  item.type = "text";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_TEXT++);
  InputTextField.call(this, parent_id, item);

  this.label = item.label || "Please enter some texts...";

  this.html = this._generate();
  this.render();
}
InputText.prototype = inherit(InputTextField.prototype);

function InputTime(parent_id, item = {}) {
  item.type = "time";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_TIME++);
  InputTextField.call(this, parent_id, item);

  this.name = item.name || this.id;
  this.html = this._generate();
  this.render();
}
InputTime.prototype = inherit(InputTextField.prototype);

function InputUrl(parent_id, item = {}) {
  item.type = "url";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_URL++);
  InputTextField.call(this, parent_id, item);

  this.label = item.label || "Please enter the url...";

  this.html = this._generate();
  this.render();
}
InputUrl.prototype = inherit(InputTextField.prototype);

function InputWeek(parent_id, item = {}) {
  item.type = "week";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_WEEK++);
  InputTextField.call(this, parent_id, item);

  this.html = this._generate();
  this.render();
}
InputWeek.prototype = inherit(InputTextField.prototype);

/*
############################################################
# Textarea **inherits** InputTextField
# Textarea does the following:

#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- True or assigned by user
#
# @param parent_id --> the id of its parent element
# @param item --> a set of values for setting
# @param item.name --> html attribute
# @param item.placeholder --> html attribute
# @param item.cols --> html attribute
# @param item.rows --> html attribute
# @param item.wrap --> html attribute
############################################################
# @return
#
############################################################
*/
function Textarea(parent_id, item = {}) {
  item.type = "textarea";
  item.id = item.id || "{0}_{1}".format(item.type, __TEXTAREA++);
  InputTextField.call(this, parent_id, item);

  this.name = item.name || this.id;
  this.placeholder = item.placeholder || "Text lines";
  this.cols = item.cols || "30";
  this.rows = item.rows || "10";
  this.wrap = (item.wrap) ? 'wrap="' + item.wrap + '" ' : "";

  this.html = this._generate();
  this.render();
}
Textarea.prototype = inherit(Tag.prototype);
Textarea.prototype._generate = function() {
  var component = '<textarea class="mdl-textfield__input" id="{0}" rows={1} columns={2} form="{4}" maxlength="{4}" {5} {6} {7} {8} {9} name="{10}"></textarea>'.format(
    this.id, this.rows, this.cols, this.parent_id, this.maxlength, this.readonly, this.required, this.disabled, this.autofocus, this.wrap, this.name) +
  '<label class="mdl-textfield__label" for="{0}">{1}</label>'.format(this.id, this.placeholder);

  return '<div class="{0}">{1}<div>'.format(this.style, component);
}

/*
############################################################
# Toggle **inherits** Tag
# Toggle does the following: as a superclass for checkbox, radio, switch

#
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- True or assigned by user
#
# @param parent_id --> the id of its parent element
# @param item --> a set of values for setting
# @param item.ripple --> see MDL attribute
# @param item.checked --> html attribute
############################################################
# @return
#
############################################################
*/
function Toggle(parent_id, item = {}) {
  item.newline = item.newline || false;
  Tag.call(this, parent_id, item);

  // settings for mdl
  // this.type --> type as a <input> tag 
  // this.type_class --> template of different type class in mdl i.e. checkbox switch...
  // this.content_class --> template of different content class in mdl  
  this.ripple = (item.ripple == false) ? false : true;
  this.toggle_type = item.toggle_type;

  this.value = item.value || this.label;

  this.checked = (item.checked) ? 'checked="checked"' : "";
}
Toggle.prototype = inherit(Tag.prototype);
Toggle.prototype._generate = function() {
  var addon = "";
  addon += ((this.ripple) ? " mdl-js-ripple-effect" : "");

  var html = '<label for="{0}" class="mdl-{1} mdl-js-{1}{2}">'.format(
    this.id, this.toggle_type, addon
    );
  html += '<input type="{0}" id="{1}" class="{2}" form="{3}" {4} {5} {6} value="{7}" name="{8}" {9}>'.format(
    this.type, this.id, this.type_class, this.parent_id, 
    this.checked, this.autofocus, this.required, 
    this.value, this.name, this.readonly
    );
  html += this.content_class + '</label>'

  return html;
};

function Checkbox(parent_id, item = {}) {
  item.type = "checkbox";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_CHECKBOX++);
  item.toggle_type = "checkbox";
  item.label = item.label || "Checkbox #{0}".format(__INPUT_CHECKBOX - 1);
  Toggle.call(this, parent_id, item);

  this.type_class = 'mdl-checkbox__input';
  var isImage = this.label.startsWith("<div") && this.label.endsWith("</div>")
  if (!isImage)
    this.content_class = '<span class="mdl-checkbox__label">{0}</span>'.format(this.label);
  else
    this.content_class = '<span class="mdl-checkbox__label">{0}</span>'.format(this.value);

  this.html = this._generate();
  if (isImage)
    this.html += this.label;
}
Checkbox.prototype = inherit(Toggle.prototype);

function Switch(parent_id, item = {}) {
  item.type = "checkbox";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_CHECKBOX++);
  item.toggle_type = "switch";
  item.label = item.label || "Switch #{0}".format(__INPUT_CHECKBOX - 1);
  Toggle.call(this, parent_id, item);

  this.type_class = 'mdl-switch__input';
  var isImage = this.label.startsWith("<div") && this.label.endsWith("</div>")
  if (!isImage)
    this.content_class = '<span class="mdl-switch__label">{0}</span>'.format(this.label);
  else
    this.content_class = '<span class="mdl-switch__label">{0}</span>'.format(this.value);

  this.html = this._generate();
  if (isImage)
    this.html += this.label;
}
Switch.prototype = inherit(Toggle.prototype);

function Radio(parent_id, item = {}) {
  item.type = "radio";
  item.id = item.id || "{0}_{1}".format(item.type, __INPUT_RADIO++);
  item.toggle_type = "radio";
  item.label = item.label || "Radio #{0}".format(__INPUT_RADIO - 1);
  Toggle.call(this, parent_id, item);

  this.type_class = 'mdl-radio__button';
  var isImage = this.label.startsWith("<div") && this.label.endsWith("</div>")
  if (!isImage)
    this.content_class = '<span class="mdl-radio__label">{0}</span>'.format(this.label);
  else
    this.content_class = '<span class="mdl-radio__label">{0}{1}</span>'.format(this.value, this.label);

  this.html = this._generate();
}
Radio.prototype = inherit(Toggle.prototype);


/*
############################################################
# ToggleGroup **inherits** Tag
# ToggleGroup does the following: renders a group of toggles
# **note:
#        if item.values is not assigned, it will first take values
#        from item.labels and then from item.images until 
#        item.values have the same size as item.labels + item.images
#        
# Arbitrary settings:
# item.type <-- automatically assigned
# item.id   <-- automatically assigned
# item.needQuestion <-- True or assigned by user
#
# @param parent_id --> the id of its parent element
# @param item --> a set of values for setting
# @param item.images --> an array of images
# @param item.values --> an array of values
# @param item.labels --> an array of labels
############################################################
# @return
#
############################################################
*/
function ToggleGroup(parent_id, item) {
  item.id = item.id || "Toggle group_{0}".format(__TOGGLE_GROUP++);
  item.type = item.type || "checkbox";
  item.name = item.name || item.id;
  item.needQuestion = (item.needQuestion == false) ? false : true;
  item.images = item.images || [];
  item.labels = item.labels || [];
  item.values = item.values || [];
  Tag.call(this, parent_id, item);

  for (var i in item.labels) {
    if (item.values.length < item.labels.length)
      item.values = [item.labels[i]] + item.values;
  }
  if (item.images.length > 0) {
    for (var i in item.images) {
      item.labels.push('<div class="mdl-card mdl-shadow--2dp" \
        style="width: 256px;height: 256px;background: url({0}) center/cover;"></div>'.format(item.images[i]));
      if (item.values.length < item.labels.length)
        item.values.push(item.images[i]);
    }
  }
  this.values = item.values;
  this.labels = item.labels;

  this.html = "";
  var factory = this._selector();

  for (var i in this.labels) {
    item.label = this.labels[i];
    item.value = this.values[i];
    item.id = ""; // initialize item.id
    this.html += factory(this.parent_id, item).html + "\n";
  }
  this.html = "<br><div>" + this.html + "</div><br>"
  this.render();
}
ToggleGroup.prototype = inherit(Tag.prototype);
ToggleGroup.prototype._selector = function() {
  switch (this.type) {
    case "checkbox":
    return function(parent_id, item) {
      return new Checkbox(parent_id, item);
    }
    case "switch":
    return function(parent_id, item) {
      return new Switch(parent_id, item);
    }
    case "radio":
    return function(parent_id, item) {
      return new Radio(parent_id, item);
    }
  }
}


createForm(display_element, trial);

    // data saving
    var trial_data = {
      parameter_name: 'parameter value'
    };

    // end trial
    jsPsych.finishTrial(trial_data);
  };




  return plugin;
})();