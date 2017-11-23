'use strict';

import gulp from 'gulp';
import gulpUtil from 'gulp-util';
import uglify from 'gulp-uglify';
import concat from 'gulp-concat';
import cleanCSS from 'gulp-clean-css';
import del from 'del';

const DIR = {
    SRC: 'src/main/resources/static/module',
    DEST: 'src/main/resources/static/dist'
};

const SRC = {
    JS: DIR.SRC + '/js/*.js',
    CSS: DIR.SRC + '/css/*.css'
};

const DEST = {
    JS: DIR.DEST + '/js',
    CSS: DIR.DEST + '/css'
};

gulp.task('default', ['clean', 'copy', 'concat-js-lib', 'concat-css-lib'], () => {
    gulpUtil.log('Gulp is running');
});

gulp.task('clean', () => {
    return del.sync([DIR.DEST]);
});

gulp.task('concat-js-lib', function() {
    return gulp.src(DIR.SRC+'/js/lib/*.js')
        .pipe(concat('lib.js'))
        .pipe(uglify())
        .pipe(gulp.dest(DEST.JS));
});

gulp.task('concat-css-lib', function() {
    return gulp.src(DIR.SRC+'/css/lib/*.css')
        .pipe(concat('lib.css'))
        .pipe(cleanCSS({compatibility: 'ie8'}))
        .pipe(gulp.dest(DEST.CSS));
});

gulp.task('copy', function () {
    gulp.src('bower_components/jquery/dist/jquery.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/tui-code-snippet/dist/tui-code-snippet.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/markdown-it/dist/markdown-it.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/toMark/dist/toMark.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/codemirror/lib/codemirror.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/highlightjs/highlight.pack.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/squire-rte/build/squire-raw.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));
    gulp.src('bower_components/tui-editor/dist/tui-editor.min.js').pipe(gulp.dest(DIR.SRC+'/js/lib'));

    gulp.src('bower_components/codemirror/lib/codemirror.css').pipe(gulp.dest(DIR.SRC+'/css/lib'));
    gulp.src('bower_components/highlightjs/styles/github.css').pipe(gulp.dest(DIR.SRC+'/css/lib'));
    gulp.src('bower_components/tui-editor/dist/tui-editor.css').pipe(gulp.dest(DIR.SRC+'/css/lib'));
    gulp.src('bower_components/tui-editor/dist/tui-editor-contents.css').pipe(gulp.dest(DIR.SRC+'/css/lib'));
});