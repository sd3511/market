angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.showProductInfo = function (productId) {
        $http.get(contextPath+ 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }
    $scope.addToCart = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/add/' + productId).then(function (response) {
        });
    }
    $scope.loadProducts = function (pa) {
        console.log($scope.filter)
        $http({
            url: 'http://localhost:5555/core/api/v1/products',
            method: 'GET',
            params: {
                min: $scope.filter ? $scope.filter.minPrice : null,
                max: $scope.filter ? $scope.filter.maxPrice : null,
                title: $scope.filter ? $scope.filter.title : null,
                p:pa
            }

        }).then(function (response) {
            console.log(response)
            $scope.productsList = response.data.content
            $scope.totalPages = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber+1;
        });
    }



    $scope.loadProducts();
});