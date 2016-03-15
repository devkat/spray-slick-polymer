/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
/// <reference path="user.ts" />
/// <reference path="../common/form-validation.ts" />

@component("user-form")
@behavior(FormValidation)
class UserForm extends polymer.Base implements FormValidation {

  private _emptyUser: User = { id: 0, email: "", givenName: "", familyName: "" };

  @property({ name: "user-id", observer: "_userChanged" })
  user: User;

  @property({ name: "user-id", observer: "_userIdChanged" })
  userId: number;

  private _form(): HTMLFormElement {
    return this.$.userForm;
  }

  _userChanged(): void {
    this.$.loader.close();
  }

  _userIdChanged(): void {
    //this.user = this._emptyUser;
    this._form().reset();
    [].forEach.call(this.querySelectorAll('.control'), (elem: PaperInput) =>
      elem.invalid = false
    );
    this.$.loader.open();
    setTimeout(() => this.$.userDetailsService.generateRequest(), 0);
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

    form.addEventListener('iron-form-response', () =>
      this.fire('change'));

    this.$.loader.autoFitOnAttach = true;
    this.$.loader.fitInto = form;
    this.$.loader.addEventListener('iron-overlay-opened', () =>
      form.appendChild(this.$.loader.backdropElement));

    this.validationService = this.$.userValidationService;
  }

  submitHandler(): void {
    this._form().submit();
  }

  // FormValidation behavior
  @property({ name: "validation-service", type: "String" })
  validationService: any;
  handleValidation: (result: ValidationResult) => void;
  serialize(): Object {
    let json = this.querySelector('form').serialize();
    if (this.user) {
      json.id = this.user.id;
    }
    return json;
  }

}

UserForm.register();