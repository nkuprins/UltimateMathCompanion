
class FormView {

    _btnBack = document.querySelector('.expr-form__back');
    _btnSubmit = document.querySelector('.expr-form__submit');
    _exprForm = document.querySelector('.expr-form')
    _exprFormText = document.querySelector('.expr-form__textfield')
    _expressionsTable = document.querySelector('.table__wrapper')
    _expressionsTableHeader = document.querySelector('.table__head')

    addHandlerFormText() {
        this._exprFormText.addEventListener('input', this._handleFormText.bind(this))
    }

    addHandlerBtnSubmit() {
        this._btnSubmit.addEventListener('click', this._handleBtnSubmit.bind(this));
    }

    addHandlerBtnBack() {
        this._btnBack.addEventListener('click', this._handleBtnBack.bind(this));
    }

    _handleBtnBack() {
        this._exprForm.style.display = 'none';
        this._expressionsTable.style.display = 'inherit';
        this._expressionsTableHeader.style.display = 'flex';
    }

    _handleFormText() {
        this._exprFormText.setCustomValidity('');
    }

    _handleBtnSubmit() {
        const expressionPattern = /^-?\d+( [+\-*/] -?\d+)+$/;
        if (!expressionPattern.test(this._exprFormText.value)) {
            this._exprFormText.setCustomValidity(this._customErrorPopup());
            return false;
        }

        return true;
    }

    _customErrorPopup() {
        const text = this._exprFormText.value;
        if (text === '')
            return 'The text is empty';

        return 'Possible mistakes:\n' +
            '1. Invalid symbols like: AB$!^&\n' +
            '2. DECIMAL number\n' +
            '3. Not enough spaces or they are redundant';
    }

}

export default new FormView();