/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var FormValidation = (function (_super) {
    __extends(FormValidation, _super);
    function FormValidation() {
        _super.apply(this, arguments);
    }
    FormValidation.prototype.handleError = function (result) {
        [].forEach.call(this.querySelectorAll('.control'), function (elem) {
            var errors = result[elem.getAttribute("name")];
            if (errors) {
                elem.errorMessage = errors[0];
                elem.invalid = true;
            }
            else {
                elem.errorMessage = null;
                elem.invalid = false;
            }
        });
    };
    return FormValidation;
}(polymer.Base));
