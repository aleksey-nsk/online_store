angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8081/api/v1';
    console.log("contextPath: " + contextPath);

    $scope.logout = function () {
        const url = '/logout';
        console.log("Method logout(), url: " + url);
        $http.get(url);
    };

    $scope.fillTable = function (pageIndex = 1) {
        const url = contextPath + '/product';
        console.log("Method fillTable(), url: " + url);

        $http({
            url: url,
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                pageIndex: pageIndex
            }
        }).then(function (response) {
            $scope.ProductPage = response.data;

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductPage.totalPages) {
                maxPageIndex = $scope.ProductPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            $scope.getCategories();
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        console.log("Method generatePagesIndexes(), startPage=" + startPage + ", endPage=" + endPage);
        let arr = [];
        for (let i = startPage; i <= endPage; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.getCategories = function () {
        const url = contextPath + '/category';
        console.log("Method getCategories(), url: " + url);
        $http.get(url)
                .then(function (resp) {
                    $scope.Categories = resp.data;
                });
    };

    $scope.showCart = function () {
        const url = contextPath + '/cart';
        console.log("Method showCart(), url: " + url);
        $http({
            url: url,
            method: 'GET'
        }).then(function (response) {
            $scope.Cart = response.data;
        });
    };

    $scope.clearCart = function () {
        const url = contextPath + '/cart';
        console.log("Method clearCart(), url: " + url);
        $http.delete(url)
                .then(function (response) {
                    $scope.showCart();
                });
    };

    $scope.changePrice = function (product, newPrice) {
        const url = contextPath + '/product/' + product.id;
        console.log("Method changePrice(), url: " + url + ", newPrice=" + newPrice);

        // Создать новый пустой объект
        let newProduct = {};

        // Скопировать все свойства product в новый объект
        for (let key in product) {
            newProduct[key] = product[key]
        }

        // Изменить цену
        newProduct.price = newPrice;

        $http.put(url, newProduct)
                .then(function (resp) {
                    $scope.fillTable()
                });
    };

    $scope.addToCart = function (productId) {
        const url = contextPath + '/cart?productId=' + productId;
        console.log("Method addToCart(), url: " + url);
        $http.put(url)
                .then(function (response) {
                    $scope.showCart();
                });
    };

    $scope.deleteProduct = function (productId) {
        const url = contextPath + '/product/' + productId;
        console.log("Method deleteProduct(), url: " + url);
        $http.delete(url)
                .then(function (response) {
                    $scope.fillTable();
                });
    };

    $scope.saveProduct = function () {
        const url = contextPath + '/product';
        console.log("Method saveProduct(), url: " + url);

        $scope.NewProduct.category = $scope.NewCategory;
        console.log($scope.NewProduct);
        console.log("Название: '" + $scope.NewProduct.title + "'"
                + ", категория: '" + ($scope.NewProduct.category ? $scope.NewProduct.category.name : null) + "'"
                + ", цена: " + $scope.NewProduct.price);

        $http.post(url, $scope.NewProduct)
                .then(function (response) {
                    $scope.NewProduct = null;
                    $scope.NewCategory = null;
                    $scope.fillTable();
                });
    };

    $scope.fillTable();
    $scope.showCart();

});
