(function () {

    "use strict";

    function AltairFx(el) {
        this.el = el;

        this.stack = el.querySelector(".stack");

        this.stackItems = Array.from(
            this.stack.children
        );

        this.img = this.stack.querySelector(".stack__img");

        this.caption = el.querySelector(".grid__item-caption");

        this.title = this.caption.querySelector(".grid__item-title");

        this.columns = this.caption.querySelectorAll(".column");

        this.initEvents();
    }


    AltairFx.prototype.initEvents = function () {

        const self = this;

        this.stack.addEventListener("mouseenter", function () {
            self.animateIn();
        });

        this.stack.addEventListener("mouseleave", function () {
            self.animateOut();
        });

    };


    AltairFx.prototype.animateIn = function () {

        const self = this;

        anime.remove(this.stackItems);
        anime.remove(this.img);
        anime.remove(this.title);
        anime.remove(this.columns);


        this.stackItems.forEach(function (item, index) {

            item.style.opacity =
                index !== self.stackItems.length - 1
                    ? 0.2 * index + 0.2
                    : 1;

        });


        anime({

            targets: this.stackItems,

            duration: 1000,

            easing: "easeOutElastic",

            translateZ: function (target, index) {
                return index * 3;
            },

            rotateX: function (target, index) {
                return -index * 4;
            },

            delay: function (target, index, count) {
                return (count - index - 1) * 30;
            }

        });


        anime({

            targets: this.img,

            duration: 500,

            easing: "easeOutExpo",

            scale: 0.7

        });


        anime({

            targets: this.title,

            duration: 1000,

            easing: "easeOutElastic",

            translateY: 20

        });


        anime({

            targets: this.columns,

            duration: 1000,

            easing: "easeOutElastic",

            translateY: function (target, index) {
                return index === 0 ? 30 : 20;
            }

        });

    };


    AltairFx.prototype.animateOut = function () {

        anime.remove(this.stackItems);
        anime.remove(this.img);
        anime.remove(this.title);
        anime.remove(this.columns);


        anime({

            targets: this.stackItems,

            duration: 500,

            easing: "easeOutExpo",

            opacity: function (target, index, count) {
                return index !== count - 1 ? 0 : 1;
            },

            translateZ: 0,

            rotateX: 0

        });


        anime({

            targets: this.img,

            duration: 500,

            easing: "easeOutExpo",

            scale: 1

        });


        anime({

            targets: [
                this.title,
                ...this.columns
            ],

            duration: 500,

            easing: "easeOutExpo",

            translateY: 0

        });

    };


    document
        .querySelectorAll(".grid--effect-altair .grid__item")
        .forEach(function (item) {

            new AltairFx(item);

        });

})();