/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
// <reference path="../../bower_components/polymer-elements-typings/typings/paper-input/paper-input.d.ts" />

interface ValidationResult {
  [key: string]: string[];
}

class FormValidation extends polymer.Base {

  @property({ name: "validationService", observer: "validationServiceChanged" })
  validationService: any;

  validationServiceChanged(): void {
    this.validationService.method = "POST";
    this.validationService.contentType = "application/json";
    this.validationService.addEventListener('response', (event: Event) =>
      this.handleValidationSuccess());
    this.validationService.addEventListener('error', (event: Event) =>
      this.handleValidationError(event, false));
  }

  ready(): void {

    // Register callback for form submission errors
    let form = this.querySelector('form');
    form.addEventListener('iron-form-error', (event: Event) =>
      this.handleValidationError(event, true));

    // Listen for changes in form controls
    [].forEach.call(this.querySelectorAll('.control'), (elem: HTMLElement) =>
      elem.addEventListener('change', (e:Event) =>
        this.validate(<HTMLElement> e.target))
    );

  }

  serialize(): Object {
    return null;
  }

  validate(control: HTMLElement): void {
    let name = control.name;
    let value = control.value;

    this.validationService.body = this.serialize();
    this.validationService.generateRequest();
  }

  handleValidationSuccess(): void {
    [].forEach.call(this.querySelectorAll('.control'), (elem: HTMLElement) => {
      elem.errorMessage = null;
      elem.invalid = false;
    });
  }

  /**
   * Handles a validation error response.
   *
   * @param event The error event.
   * @param partial Whether the form was submitted.
   */
  handleValidationError(event: Event, submitted: boolean): void {
    let xhr = event.detail.request.xhr;
    if (xhr.status === 400) {
      this.showValidation(xhr.response, submitted);
    } else {
      console.log("Validation request error", xhr);
    }
  }

  showValidation(result: ValidationResult, submitted: boolean): void {
    [].forEach.call(this.querySelectorAll('.control'), (elem: HTMLElement) => {
      // Don't show validation if field wasn't filled in yet
      if (submitted || elem.value !== undefined) {
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
  }

}