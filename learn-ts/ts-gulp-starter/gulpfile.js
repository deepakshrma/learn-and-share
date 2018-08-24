var gulp = require("gulp");
var browserify = require("browserify");
var watchify = require("watchify");
var source = require("vinyl-source-stream");
var tsify = require("tsify");
var gutil = require("gulp-util");
var uglify = require("gulp-uglify");
var buffer = require("vinyl-buffer");
var sourcemaps = require("gulp-sourcemaps");
var paths = {
  pages: ["src/*.html"]
};
var watchedBrowserify = watchify(
  browserify({
    basedir: ".",
    debug: true,
    entries: ["src/main.ts"],
    cache: {},
    packageCache: {}
  }).plugin(tsify)
);

gulp.task("copy-html", function() {
  return gulp.src(paths.pages).pipe(gulp.dest("dist"));
});
function bundle() {
  return watchedBrowserify
    .transform("babelify", {
      presets: ["es2015"],
      extensions: [".ts"]
    })
    .bundle()
    .pipe(source("bundle.js"))
    .pipe(buffer())
    .pipe(sourcemaps.init({ loadMaps: true }))
    .pipe(uglify())
    .pipe(sourcemaps.write("./"))
    .pipe(gulp.dest("dist"));
}

gulp.task("default", ["copy-html"], bundle);
watchedBrowserify.on("update", bundle);
watchedBrowserify.on("log", gutil.log);
