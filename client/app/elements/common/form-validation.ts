/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />

interface ValidationResult {
  [key: string]: string[];
}

interface PaperInput extends HTMLElement {
  errorMessage: string;
  invalid: boolean;
}

class FormValidation extends polymer.Base {

  handleError(result: ValidationResult): void {
    [].forEach.call(this.querySelectorAll('.control'), (elem: PaperInput) => {
      let errors: string[] = result[elem.getAttribute("name")];
      if (errors) {
        elem.errorMessage = errors[0];
        elem.invalid = true;
      } else {
        elem.errorMessage = null;
        elem.invalid = false;
      }
    });
  }

}