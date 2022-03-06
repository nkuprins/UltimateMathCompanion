import sliderView from './view/sliderView.js';
import sectionView from './view/sectionView.js';
import formView from './view/formView.js';
import tableView from './view/tableView.js';
import navView from './view/navView.js';

const init = function () {
    navView.addHandlerNavHover();
    navView.addHandlerBtnAdd();
    navView.addHandlerBtnDelete();
    navView.addHandlerBtnEdit();
    formView.addHandlerBtnBack();
    formView.addHandlerBtnSubmit();
    formView.addHandlerFormText();
    sectionView.observeSection();
    sectionView.observeImages();
    sliderView.addHandlerBtnLeft();
    sliderView.addHandlerBtnRight();
    sliderView.addHandlerDotContainer();
    tableView.addHandlerTableRow();
};

init();
