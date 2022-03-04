
class NavView {
    _navEl = document.querySelector('.expressions-nav');
    _btnAdd = document.querySelector('.add')
    _btnEdit = document.querySelector('.edit')
    _btnDelete = document.querySelector('.delete')
    _exprForm = document.querySelector(".expr-form")
    _expressionsTable = document.querySelector(".table__wrapper")
    _expressionsTableHeader = document.querySelector(".table__head")

    constructor() {
        this._promiseTableView = (async () => (await import('./tableView.js')).default)();
    }

    addHandlerNavHover() {
        this._navEl.addEventListener('mouseover', this._handleNavHover.bind(0.5));
        this._navEl.addEventListener('mouseout', this._handleNavHover.bind(1));
    }

    addHandlerBtnAdd() {
        this._btnAdd.addEventListener('click', this._handleBtnAdd.bind(this))
    }

    addHandlerBtnDelete() {
        this._btnDelete.addEventListener('click', this._handleBtnDelete.bind(this))
    }

    _handleBtnAdd() {
        this._exprForm.style.display = 'inherit';
        this._expressionsTable.style.display = 'none';
        this._expressionsTableHeader.style.display = 'none';
    }

    _handleBtnDelete() {
        this._promiseTableView.then(tableView =>
        {
            window.location.href = '/deleteExpression?id=' + tableView.getSelectedId ;
        });
    }

    _handleNavHover(e) {
        if (!e.target.closest('.expressions-nav__item')?.classList.contains('expressions-nav__item'))
            return;

        const link = e.target.closest('.expressions-nav__item');
        const siblings = link.closest('.expressions-nav').querySelectorAll('.expressions-nav__item');
        siblings.forEach(el => {
            if (el !== link) el.style.opacity = this;
        });
    }
}

export default new NavView();