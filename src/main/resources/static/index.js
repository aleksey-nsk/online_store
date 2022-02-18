angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8082/api/v1';

    $scope.logout = function () {
        $http.get('/logout');
    };

    $scope.fillTable = function (pageIndex = 1) {
        $http({
            url: contextPath + '/product',
            method: 'GET',
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                p: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductsPage.totalPages) {
                maxPageIndex = $scope.ProductsPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.showCart = function(){
        $http({
            url: contextPath + '/cart',
            method: 'GET'
        }).then(function (response){
            $scope.Cart = response.data;
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++){
            arr.push(i);
        }
        return arr;
    };

    $scope.submitCreateNewProduct = function (){
        $http.post(contextPath + '/product', $scope.newProduct)
                .then(function (response){
                    $scope.newProduct = null;
                    $scope.fillTable();
                });
    };

    $scope.deleteProductById = function (productId){
        $http.delete(contextPath + '/products/' + productId)
                .then(function (response){
                    $scope.fillTable();
                });
    };

    $scope.addToCart = function (productId) {
        $http.put(contextPath + '/cart?productId=' + productId)
                .then(function (response){
                    $scope.showCart();
                });
    };

    $scope.clearCart = function (){
        $http.delete(contextPath + '/cart')
                .then(function (response){
                    $scope.showCart();
                });
    };

    $scope.fillTable();
    $scope.showCart();

    // $scope.getCategories = function () {
    //     $http.get(contextPath + '/category')
    //             .then(function (resp) {
    //                 $scope.Categories = resp.data;
    //             });
    // };
    //
    // $scope.fillTable = function () {
    //     $http.get(contextPath + '/product')
    //             .then(function (resp) {
    //                 console.log(resp); // лог в консоль браузера (F12 - Console)
    //                 $scope.Products = resp.data;
    //                 $scope.getCategories();
    //             });
    // };
    //
    // $scope.saveProduct = function () {
    //     $scope.NewProduct.category = $scope.NewCategory;
    //     console.log($scope.NewProduct);
    //     $http.post(contextPath + '/product', $scope.NewProduct)
    //             .then(function (resp) {
    //                 $scope.fillTable();
    //             });
    // };

    // $scope.deleteProduct = function (id) {
    //     $http.delete(contextPath + '/product/' + id)
    //             .then(function (resp) {
    //                 $scope.fillTable();
    //             });
    // };
    //
    // $scope.doSort = function () {
    //     $http.post(contextPath + '/product/sort', $scope.NewSorted)
    //             .then(function (resp) {
    //                 $scope.Products = resp.data;
    //             });
    // };
    //
    // $scope.changePrice = function (product, new_price) {
    //     product.price = new_price;
    //     console.log(product)
    //     $http.put(contextPath + '/product/' + product.id, product)
    //             .then(function (resp) {
    //                 console.log(resp);
    //                 $scope.fillTable()
    //             });
    // };
    //
    // $scope.fillTable();

});
