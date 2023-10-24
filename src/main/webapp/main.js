const isPageReloaded = performance.navigation.type === performance.navigation.TYPE_RELOAD;

let intro = document.querySelector(".intro");
let logo = document.querySelector(".logo-header");
let logoSpan = document.querySelectorAll(".logo");

if (isPageReloaded) {
    window.addEventListener("DOMContentLoaded", () => {
        setTimeout(() => {
            logoSpan.forEach((span, idx) => {
                setTimeout(() => {
                    span.classList.add("active");
                }, idx === 1 ? 800 : 400); // Delay for index 0 and 2 is 400ms, and for index 1 is 800ms
            });

            setTimeout(() => {
                logoSpan.forEach((span, idx) => {
                    setTimeout(() => {
                        span.classList.remove("active");
                        span.classList.add("fade");
                    }, (idx + 1) * 50)
                });
            }, 2000);

            setTimeout(() => {
                intro.style.top = "-100vh";
            }, 2300);
        });
    });
} else {
    intro.style.display = "none";
}

