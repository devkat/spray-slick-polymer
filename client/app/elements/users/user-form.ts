/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
/// <reference path="user.ts" />
/// <reference path="../common/form-validation.ts" />

@component("user-form")
@behavior(FormValidation)
class UserForm extends polymer.Base {

  user: User;

  @property({ name: "user-id", observer: "_loadUser" })
  userId: number;

  private _form(): HTMLFormElement {
    return this.$.userForm;
  }

  _loadUser(): void {
    this.$.ajax.generateRequest();
  }

  ready(): void {
    let component = this;
    let form = this._form();
    form.addEventListener('iron-form-presubmit', function() {
      let userId = component.userId;
      if (userId) {
        this.request.method = 'PUT';
        this.request.url = `/api/users/${userId}`;
      }
      this.request.headers['Content-Type'] = 'application/json';
      this.request.body = form.serialize();
    });

    form.addEventListener('iron-form-response',
      () => this.fire('change'));

    form.addEventListener('iron-form-error',
      (event) => this.handleError(event.detail.request.xhr.response));
  }

  submitHandler(): void {
    this._form().submit();
  }

  handleError: (result:ValidationResult) => void;
}

UserForm.register();