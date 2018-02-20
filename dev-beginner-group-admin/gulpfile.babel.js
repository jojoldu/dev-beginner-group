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

const DEST = {
    JS: DIR.DEST + '/js',
    CSS: DIR.DEST + '/css',
    FONTS: DIR.DEST + '/fonts'
};

gulp.task('default', ['clean', 'copy', 'minify-js-lib', 'minify-css-lib'], () => {
    gulpUtil.log('Gulp is completed');
});

gulp.task('clean', () => {
    return del.sync([DIR.DEST]);
});

gulp.task('minify-js-lib', () => {
    return gulp.src([
        'node_modules/jquery/dist/jquery.js',
        'node_modules/tui-editor/dist/tui-editor-Editor.js'
    ])
        .pipe(concat('lib.js'))
        .pipe(uglify())
        .pipe(gulp.dest(DEST.JS));
});

gulp.task('minify-css-lib', () => {
    return gulp.src([
        'node_modules/tui-editor/dist/tui-editor.css',
        'node_modules/tui-editor/dist/tui-editor-contents.css'
    ])
        .pipe(concat('lib.css'))
        .pipe(cleanCSS({compatibility: 'ie8'}))
        .pipe(gulp.dest(DEST.CSS));
});

gulp.task('copy', () => {
    gulp.src('node_modules/bootstrap/dist/fonts/*').pipe(gulp.dest(DEST.FONTS));
});