/**
 * Created by jojoldu@gmail.com on 2018. 2. 20.
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

const webpack = require('webpack');
const path = require('path');

const defaultPath = 'src/main/resources/static';

const config = {
    context: path.resolve(__dirname, defaultPath),
    entry: {
        vendor: ['bootstrap', 'bootstrap/dist/css/bootstrap.min.css'],
        'content.save':['./js/content/save.js']
    },
    output: {
        path: path.resolve(__dirname, defaultPath),
        filename: './dist/js/[name].bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                include: path.resolve(__dirname, defaultPath),
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            ['es2015', {modules: false}]
                        ]
                    }
                }]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|jpg|jpeg|gif)$/,
                loader: "file-loader?name=/css/build/image/[name].[ext]"
            },
            {
                test: /\.(svg|woff|woff2|ttf|eot)$/,
                loader: "file-loader?name=/css/build/fonts/[name].[ext]"
            },
            {
                test: /\.hbs/,
                loader: "handlebars-loader"
            }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery"
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: "vendor"
        })
    ],
    devtool: 'inline-source-map',
    devServer: {
        historyApiFallback: true,
        compress: true,
        publicPath: '/',
        host: "0.0.0.0",
        port: 3000,
        proxy: {
            "**": "http://localhost:8080"
        }
    }
};

module.exports = config;