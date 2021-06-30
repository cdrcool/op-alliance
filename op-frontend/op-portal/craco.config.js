const TerserPlugin = require("terser-webpack-plugin");

module.exports = {
    optimization: {
        minimize: false,
        minimizer: [
            new TerserPlugin({
                terserOptions: {
                    sourceMap: true,
                    compress: {
                        // pure_funcs: ["console.log"]
                    },
                }
            })
        ],
    },
};