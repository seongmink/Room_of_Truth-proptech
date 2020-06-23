
module.exports = {
  //eslintrc 없애기
  lintOnSave: false,
  // "transpileDependencies": [
  //   "vuetify"
  // ],
  devServer: {
    proxy: 'http://localhost:8081'
  }

}