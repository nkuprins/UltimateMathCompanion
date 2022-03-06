
class SliderView {

    _slides = document.querySelectorAll('.slide');
    _btnLeft = document.querySelector('.slider__btn--left');
    _btnRight = document.querySelector('.slider__btn--right');
    _dotContainer = document.querySelector('.dots');

    constructor() {
        this._curSlide = 0;
        this._maxSlide = this._slides.length;

        this._createDots();
        this._goToSlide(0);
        this._activateDot(0);
    }

    _nextSlide() {
        if (this._curSlide === this._maxSlide - 1)
            this._curSlide = 0;
        else
            this._curSlide++;

        this._goToSlide(this._curSlide);
        this._activateDot(this._curSlide);
    }

    _prevSlide() {
        if (this._curSlide === 0)
            this._curSlide = this._maxSlide - 1;
        else
            this._curSlide--;

        this._goToSlide(this._curSlide);
        this._activateDot(this._curSlide);
    }

    _goToSlide(slide) {
        this._slides.forEach(
            (el, i) => {
                el.classList.toggle('slide__hidden');
                el.style.transform = `translateX(${350 * (i - slide)}%)`;
            }
        );
    }

    _createDots() {
        this._slides.forEach(function (_, i) {
             this._dotContainer.insertAdjacentHTML(
                'beforeend', `<button class="dots__dot" data-slide="${i}"></button>`
            );
        }.bind(this))
    }

    _activateDot(slide) {
        document.querySelectorAll('.dots__dot').forEach(
            dot => dot.classList.remove(`dots__dot--active`)
        );
        document.querySelector(`.dots__dot[data-slide="${slide}"]`)
            .classList.add(`dots__dot--active`);
    }

    addHandlerBtnLeft() {
        this._btnLeft.addEventListener('click', this._prevSlide.bind(this));
    }

    addHandlerBtnRight() {
        this._btnRight.addEventListener('click', this._nextSlide.bind(this));
    }

    addHandlerDotContainer() {
        this._dotContainer.addEventListener('click', function (e) {
            if (e.target.classList.contains('dots__dot')) {
                const { slide } = e.target.dataset;
                this._goToSlide(slide);
                this._activateDot(slide);
            }
        }.bind(this));
    }
}

setTimeout( () => {
    document.querySelector('.calculator').style.display = 'block';
}, 500);

export default new SliderView();