/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
// <reference path="../../bower_components/polymer-elements-typings/typings/paper-input/paper-input.d.ts" />

interface ValidationResult {
  [key: string]: string[];
}

class FormValidation extends polymer.Base {

  @property({ name: "validationService", observer: "validationServiceChanged" })
  validationService: any;

  @property({ name: "valid", type: Boolean, value: false, notify: true })
  valid: boolean = false;

  validationServiceChanged(): void {
    this.validationService.method = "POST";
    this.validationService.contentType = "application/json";
    this.validationService.addEventListener('response', (event: Event) =>
      this.handleValidationSuccess());
    this.validationService.addEventListener('error', (event: Event) =>
      this.handleValidationError(event));
  }

  ready(): void {

    // Register callback for form submission errors
    let form = this.querySelector('form');
    form.addEventListener('iron-form-error', (event: Event) =>
      this.handleValidationError(event));

    // Listen for changes in form controls
    [].forEach.call(this.querySelectorAll('.control'), (elem: PaperInput) =>
      elem.addEventListener('change', (e:Event) =>
        this.validate(<PaperInput> e.target))
    );

  }

  validate(control: PaperInput): void {
    let name = control.name;
    let value = control.value;

    //this.validationService.headers = { 'Validate-Attributes': name };
    let body = this.querySelector('form').serialize();

    // Undefined values are missing from JSON
    [].forEach.call(this.querySelectorAll('.control'), (elem: PaperInput) => {
      if (!body[elem.name]) body[elem.name] = "";
    });

    this.validationService.body = body;
    this.validationService.generateRequest();
  }

  handleValidationSuccess(): void {
    [].forEach.call(this.querySelectorAll('.control'), (elem: PaperInput) => {
      elem.invalid = false;
    });
    this.valid = true;
  }

  handleValidationError(event: Event): void {
    let xhr = event.detail.request.xhr;
    if (xhr.status === 400) {
      this.showValidation(xhr.response)
    } else {
      console.log("Validation request error", xhr);
    }
  }

  showValidation(result: ValidationResult): void {
    [].forEach.call(this.querySelectorAll('.control'), (elem: PaperInput) => {
      // Don't show validation if field wasn't filled in yet
      if (elem.value !== undefined) {
        let errors: string[] = result[elem.name];
        if (errors) {
          elem.errorMessage = errors[0];
          elem.invalid = true;
        } else {
          elem.errorMessage = null;
          elem.invalid = false;
        }
      }
    });
    this.valid = Object.keys(result).length === 0;
  }

}