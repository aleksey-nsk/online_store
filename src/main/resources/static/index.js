angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8081/api/v1';
    console.log("contextPath: " + contextPath);

    $scope.logout = function () {
        $http.get('/logout');
    };

    $scope.fillTable = function (pageIndex = 1) {
        const url = contextPath + '/product';
        console.log("fillTable() url: " + url);
        // console.log(pageIndex);

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
            // console.log("response: " + response);
            // console.log(response);

            $scope.ProductPage = response.data;
            // console.log($scope.ProductPage);

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductPage.totalPages) {
                maxPageIndex = $scope.ProductPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
            // console.log($scope.PaginationArray);

            $scope.getCategories();
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i <= endPage; i++) {
            arr.push(i);
        }
        // console.log(arr);
        // console.log("arr: " + arr);
        return arr;
    };

    $scope.getCategories = function () {
        const url = contextPath + '/category';
        console.log("getCategories() url: " + url);

        $http.get(url)
                .then(function (resp) {
                    $scope.Categories = resp.data;
                });
    };

    $scope.showCart = function () {
        const url = contextPath + '/cart';
        console.log("showCart() url: " + url);
        // console.log(url);

        $http({
            url: url,
            method: 'GET'
        }).then(function (response) {
            $scope.Cart = response.data;
            // console.log($scope.Cart);
        });
    };

    $scope.changePrice = function (product, newPrice) {
        console.log(product);
        console.log(newPrice);

        // Создадим новый пустой объект
        // и скопируем все свойства product в него
        // Затем изменим цену
        let newProduct = {};
        for (let key in product) {
            newProduct[key] = product[key]
        }
        newProduct.price = newPrice;
        console.log(newProduct);

        const url = contextPath + '/product/' + product.id;
        console.log("changePrice() url: " + url);

        $http.put(url, newProduct)
                .then(function (resp) {
                    console.log(resp);
                    $scope.fillTable()
                });
    };

    $scope.addToCart = function (productId) {
        const url = contextPath + '/cart?productId=' + productId;
        console.log("addToCart() url: " + url);

        $http.put(url)
                .then(function (response) {
                    $scope.showCart();
                });
    };

    $scope.deleteProduct = function (productId) {
        const url = contextPath + '/product/' + productId;
        console.log(url);
        $http.delete(url)
                .then(function (response) {
                    $scope.fillTable();
                });
    };

    $scope.saveProduct = function () {
        // console.log($scope.NewCategory);

        $scope.NewProduct.category = $scope.NewCategory;
        // console.log($scope.NewProduct);

        const url = contextPath + '/product';
        console.log(url);

        $http.post(url, $scope.NewProduct)
                .then(function (response) {
                    // console.log(response)
                    $scope.NewProduct = null;
                    $scope.NewCategory = null;
                    $scope.fillTable();
                });
    };

    $scope.clearCart = function () {
        const url = contextPath + '/cart';
        console.log(url);
        $http.delete(url)
                .then(function (response) {
                    $scope.showCart();
                });
    };

    $scope.fillTable();
    $scope.showCart();

});
