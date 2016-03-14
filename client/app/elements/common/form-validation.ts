interface ValidationResult {
  [key: string]: string[];
}

interface PaperInput extends HTMLElement {
  errorMessage: string;
  invalid: boolean;
}

class FormValidation extends HTMLFormElement {

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