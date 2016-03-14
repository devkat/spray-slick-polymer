/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
/// <reference path="user.ts" />
/// <reference path="../common/form-validation.ts" />
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
var UserForm = (function (_super) {
    __extends(UserForm, _super);
    function UserForm() {
        _super.apply(this, arguments);
    }
    UserForm.prototype._form = function () {
        return this.$.userForm;
    };
    UserForm.prototype._loadUser = function () {
        this.$.ajax.generateRequest();
    };
    UserForm.prototype.ready = function () {
        var _this = this;
        var component = this;
        var form = this._form();
        form.addEventListener('iron-form-presubmit', function () {
            var userId = component.userId;
            if (userId) {
                this.request.method = 'PUT';
                this.request.url = "/api/users/" + userId;
            }
            this.request.headers['Content-Type'] = 'application/json';
            this.request.body = form.serialize();
        });
        form.addEventListener('iron-form-response', function () { return _this.fire('change'); });
        form.addEventListener('iron-form-error', function (event) { return _this.handleError(event.detail.request.xhr.response); });
    };
    UserForm.prototype.submitHandler = function () {
        this._form().submit();
    };
    __decorate([
        property({ name: "user-id", observer: "_loadUser" })
    ], UserForm.prototype, "userId", void 0);
    UserForm = __decorate([
        /// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
        component("user-form"),
        behavior(FormValidation)
    ], UserForm);
    return UserForm;
}(polymer.Base));
UserForm.register();
