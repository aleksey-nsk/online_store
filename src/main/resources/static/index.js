// $scope - хранит всё, до чего можно достучаться в html-е;
// $http - через неё можно делать запросы на наш бэкенд
angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8082';

    // Пытаюсь сделать выпадающий список
    $scope.Categories = [
        {
            "id": 1,
            "name": "Категория_01"
        },
        {
            "id": 2,
            "name": "Категория_02"
        }
    ];

    $scope.fillTable = function () {
        $http.get(contextPath + '/product')
                .then(function (resp) {
                    console.log(resp);
                    $scope.Products = resp.data;
                });
    };

    $scope.saveProduct = function () {
        $http.post(contextPath + '/product', $scope.NewProduct)
                .then(function (resp) {
                    console.log(resp);
                    $scope.fillTable();
                });
    };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/product/' + id)
                .then(function (resp) {
                    console.log(resp);
                    $scope.fillTable();
                })
    }

    $scope.fillTable();

});
