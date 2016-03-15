/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
// <reference path="../../bower_components/polymer-elements-typings/typings/paper-input/paper-input.d.ts" />
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var FormValidation = (function (_super) {
    __extends(FormValidation, _super);
    function FormValidation() {
        _super.apply(this, arguments);
        this.valid = false;
    }
    FormValidation.prototype.validationServiceChanged = function () {
        var _this = this;
        this.validationService.method = "POST";
        this.validationService.contentType = "application/json";
        this.validationService.addEventListener('response', function (event) {
            return _this.handleValidationSuccess();
        });
        this.validationService.addEventListener('error', function (event) {
            return _this.handleValidationError(event);
        });
    };
    FormValidation.prototype.ready = function () {
        var _this = this;
        // Register callback for form submission errors
        var form = this.querySelector('form');
        form.addEventListener('iron-form-error', function (event) {
            return _this.handleValidationError(event);
        });
        // Listen for changes in form controls
        [].forEach.call(this.querySelectorAll('.control'), function (elem) {
            return elem.addEventListener('change', function (e) {
                return _this.validate(e.target);
            });
        });
    };
    FormValidation.prototype.validate = function (control) {
        var name = control.name;
        var value = control.value;
        //this.validationService.headers = { 'Validate-Attributes': name };
        var body = this.querySelector('form').serialize();
        // Undefined values are missing from JSON
        [].forEach.call(this.querySelectorAll('.control'), function (elem) {
            if (!body[elem.name])
                body[elem.name] = "";
        });
        this.validationService.body = body;
        this.validationService.generateRequest();
    };
    FormValidation.prototype.handleValidationSuccess = function () {
        [].forEach.call(this.querySelectorAll('.control'), function (elem) {
            elem.invalid = false;
        });
        this.valid = true;
    };
    FormValidation.prototype.handleValidationError = function (event) {
        var xhr = event.detail.request.xhr;
        if (xhr.status === 400) {
            this.showValidation(xhr.response);
        }
        else {
            console.log("Validation request error", xhr);
        }
    };
    FormValidation.prototype.showValidation = function (result) {
        [].forEach.call(this.querySelectorAll('.control'), function (elem) {
            // Don't show validation if field wasn't filled in yet
            if (elem.value !== undefined) {
                var errors = result[elem.name];
                if (errors) {
                    elem.errorMessage = errors[0];
                    elem.invalid = true;
                }
                else {
                    elem.errorMessage = null;
                    elem.invalid = false;
                }
            }
        });
        this.valid = Object.keys(result).length === 0;
    };
    __decorate([
        property({ name: "validationService", observer: "validationServiceChanged" })
    ], FormValidation.prototype, "validationService", void 0);
    __decorate([
        property({ name: "valid", type: Boolean, value: false, notify: true })
    ], FormValidation.prototype, "valid", void 0);
    return FormValidation;
}(polymer.Base));
