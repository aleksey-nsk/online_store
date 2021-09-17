// $scope - хранит всё, до чего можно достучаться в html-е;
// $http - через неё можно делать запросы на наш бэкенд
angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8082';

    // Пытаюсь сделать выпадающий список
    // $scope.Categories = [
    //     {
    //         "id": 1,
    //         "name": "Категория_01"
    //     },
    //     {
    //         "id": 2,
    //         "name": "Категория_02"
    //     }
    // ];

    $scope.getCategories = function () {
        $http.get(contextPath + '/category')
                .then(function (resp) {
                    // console.log(resp);
                    $scope.Categories = resp.data;
                });
    };

    $scope.fillTable = function () {
        $http.get(contextPath + '/product')
                .then(function (resp) {
                    // console.log(resp); // лог в консоль браузера (F12 - Console)
                    $scope.Products = resp.data;
                    $scope.getCategories();
                });
    };

    $scope.saveProduct = function () {
        console.log($scope.NewProduct);
        console.log($scope.NewCategory);

        $scope.NewProduct.category = $scope.NewCategory;
        console.log($scope.NewProduct);

        $http.post(contextPath + '/product', $scope.NewProduct)
                .then(function (resp) {
                    // console.log(resp);
                    $scope.fillTable();
                });
    };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/product/' + id)
                .then(function (resp) {
                    // console.log(resp);
                    $scope.fillTable();
                });
    };

    // $scope.getFiltered = function () {
    //     console.log($scope.NewFilter);
    //     $http.post(contextPath + '/product/filter', $scope.NewFilter)
    //             .then(function (resp) {
    //                 console.log(resp);
    //                 $scope.fillTable();
    //             });
    // };

    $scope.doSort = function () {
        // console.log($scope.NewSorted);
        $http.post(contextPath + '/product/sort', $scope.NewSorted)
                .then(function (resp) {
                    // console.log(resp);
                    $scope.Products = resp.data;
                });
    };

    $scope.changePrice = function (product, new_price) {
        product.price = new_price;
        console.log(product)

        $http.put(contextPath + '/product/' + product.id, product)
                .then(function (resp) {
                    console.log(resp);
                    $scope.fillTable()
                });
    };

    $scope.fillTable();

});
