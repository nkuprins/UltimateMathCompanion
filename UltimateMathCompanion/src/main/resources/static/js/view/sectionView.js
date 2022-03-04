// import images from "url:../images/*.png";

class SectionView {

    _allSections = document.querySelectorAll('.section');
    _imgTargets = document.querySelectorAll('img[data-name]');

    observeSection() {
        const _sectionObserver = new IntersectionObserver(this._revealSection, {
            root: null,
            threshold: 0.15
        })

        this._allSections.forEach(function (section) {
            _sectionObserver.observe(section);
            section.classList.add('section__hidden');
        })
    }

    observeImages() {
        this._imgObserver = new IntersectionObserver(this._loadImg, {
            root: null,
            threshold: 0.1
        });

        this._imgTargets.forEach(img => this._imgObserver.observe(img));
    }

    _revealSection = function (entries, observer) {
        const [entry] = entries;
        if (!entry.isIntersecting) return;

        entry.target.classList.remove('section__hidden');
        observer.unobserve(entry.target);
    }

    _loadImg(entries, observer) {
        const [entry] = entries;
        if (!entry.isIntersecting)
            return;

        entry.target.src = images[entry.target.dataset.name];
        entry.target.addEventListener('load', function () {
            entry.target.classList.remove('lazy-img');
        });
        observer.unobserve(entry.target);
    };
}

export default new SectionView();