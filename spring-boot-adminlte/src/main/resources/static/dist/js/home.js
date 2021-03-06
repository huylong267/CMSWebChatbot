// Plus number quantiy product detail
var plusQuantity = function () {
    if (jQuery('input[name="quantity"]').val() != undefined) {
        var currentVal = parseInt(jQuery('input[name="quantity"]').val());
        if (!isNaN(currentVal)) {
            jQuery('input[name="quantity"]').val(currentVal + 1);
        } else {
            jQuery('input[name="quantity"]').val(1);
        }
    } else {
        console.log('error: Not see elemnt ' + jQuery('input[name="quantity"]').val());
    }
}
// Minus number quantiy product detail 
var minusQuantity = function () {
    if (jQuery('input[name="quantity"]').val() != undefined) {
        var currentVal = parseInt(jQuery('input[name="quantity"]').val());
        if (!isNaN(currentVal) && currentVal > 1) {
            jQuery('input[name="quantity"]').val(currentVal - 1);
        }
    } else {
        console.log('error: Not see elemnt ' + jQuery('input[name="quantity"]').val());
    }
}


$(document).ready(function () {
    var modal = document.getElementById("myModal");
    var span = document.getElementsByClassName("close")[0];
    var addproduct = document.getElementsByClassName("btn-addproduct");
    var url_string = window.location.href;
    var url = new URL(url_string);
    var mid = url.searchParams.get("mid");
    if(mid != undefined || mid != null){
        localStorage.setItem("mid",mid);
    }
    var totalPrice = 0;
    $('.count').text(0)
    var countCart = $('.count').text();
    $('.myBtn').click(function () {
        modal.style.display = "block";

        var contentFood = $(this).find($('.content-food')).text().trim();
        console.log($(this).find($('.content-food')))
        var contentPrice = $(this).find($('.content-price')).text().trim();

        var productId = $(this).data('productid');
        axios.get('/api/accessory/findByProductId/' + productId).then((response) => {
            if (response.data.errorCode === '00') {

                var data = response.data.data;
                var product = data[0].products.filter(p => p.id === productId);
                $('#content-food-id').val(product[0].id);
                $('.content-food-img').attr('src', product[0].img);
                $('.content-food-name').text(product[0].name);
                $('.content-food-price').text(product[0].price);
                var items = [];
                $('#check-list-topping li').remove();
                data.map((t, index) => {
                    items.push('<li> <input type="checkbox" id="p' + index + '" name="cc"  value="' + t.id + '"><label for="p' + index + '">' + t.name + ' - </label><span>' + t.price + '</span></li>')
                });
                $('#check-list-topping').append(items.join(''));

            }else if(response.data.errorCode === '03'){
                var data = response.data.data;
            $('#content-food-id').val(data.id)
            $('.content-food-img').attr('src', data.img);
            $('.content-food-name').text(data.name);
            $('.content-food-price').text(data.price);
            } else {
                alert(response.errorDescription);
            }
        }, (error) => {
            alert(error);
        });


    })
    // span.onclick = function () {
    //     modal.style.display = "none";
    // }
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    // $('.btn-addproduct').click(function () {
    // 	modal.style.display = "none";
    // 	countCart = $('.count').text()
    // 	var countInt = parseInt(countCart);
    // 	countInt += 1;
    // 	$('.count').text(countInt);
    // 	var listTopping = []
    // 	var productName = $('.content-food-name').text();
    // 	var productPrice = $('.content-food-price').text();
    // 	var price = Number(productPrice.replace(/[^\d.]/g, ''));
    // 	var productImg = $('.content-food-img').attr('src');
    // 	var quantity = $('#updates_1057974864').val();
    //     var toppingPrice = 0;
    // 	$('.check-box-list  input:checked').each(function () {
    // 		var topping = $(this).val();
    // 		listTopping.push(topping);
    //
    //         var toppingPriceStr  = $(this).nextAll('span').text();
    //         var toppingPriceNumber = Number(toppingPriceStr.replace(/[^\d.]/g, ''));
    //         toppingPrice += toppingPriceNumber;
    // 	});
    //     var topping = listTopping.join(',');
    // 	$('.item_2').removeClass('hidden');
    // 	$('.item-cart_empty').addClass('hidden');
    // 	 totalPrice = totalPrice +  parseInt(price) * parseInt(quantity)  + toppingPrice * parseInt(quantity);
    // 	$('#total-view-cart').text(totalPrice);
    //
    // 	var $tr = $('<tr class="item_2">').append(
    // 		"<td> <img class='pro-img-cart'  src='" + productImg + "'/>",
    // 		$('<td class="pro-title-cart">').text(productName),
    // 		$('<td class="topping-title-cart">').text(topping.trim()),
    // 		$('<td class="qty-value">').text(quantity),
    // 		$('<td class="pro-price-view">').text(productPrice)
    // 	).appendTo('#clone-item-cart');
    //
    // });


    $('.btn-addproduct').click(function () {
        var productId = $('#content-food-id').val();
        var quantity = $('#updates_1057974864').val();
        var productPrice = $('.content-food-price').text();
        var price = Number(productPrice.replace(/[^\d.]/g, ''));
        var listTopping = [];
        var toppingPrice = 0;
        $('.check-box-list  input:checked').each(function () {
            var topping = $(this).val();
            console.log(topping);
            listTopping.push(topping);
            var toppingPriceStr = $(this).nextAll('span').text();
            var toppingPriceNumber = Number(toppingPriceStr.replace(/[^\d.]/g, ''));
            toppingPrice += toppingPriceNumber;
        });
		totalPrice = totalPrice +  parseInt(price) * parseInt(quantity)  + toppingPrice * parseInt(quantity);
        data = {};
        data['productId'] = productId;
        data['quantity'] = quantity;
        data['amount'] = price;
        data['accessoryId'] = listTopping;
        axios.post('/api/addtoCart', data)
            .then((response) => {
                if (response.data.errorCode === '00') {
                    modal.style.display = "none";
                    location.reload();
                } else {
                    alert(response.errorDescription);
                }

            }, (error) => {
                alert(error);
            });
    });

    $('.cart-close').click(function () {
        var cartId =$(this).data('cartid');
        axios.post('/api/removeToCart/'+cartId)
            .then((response) => {
            if (response.data.errorCode === '00') {
            location.reload();
        } else {
            alert(response.errorDescription);
        }

    }, (error) => {
            alert(error);
        });
    })

    $('#btn-check-out').click(function () {
        var data = ' ';
        // $("#clone-item-cart  tbody").find('tr').each(function (i, el) {
        //     var $tds = $(this).find('td'),
        //         productName = $tds.eq(1).text().trim(),
        //         topping = $tds.eq(2).text().trim(),
        //         quantity = $tds.eq(3).text(),
        //         price = $tds.eq(4).text()
        //
        //     data += productName + '-Topping: ' + topping + '-Số lượng : ' + quantity + '-Tổng tiền: ' + (parseInt(quantity) * parseInt(Number(price.replace(/[^\d.]/g, '')))) + " \n";
        // })

        $('.padding-right-15px').each(function (i,el) {

            var img = $(this).find('.img-cart').attr('src');
            var productName = $(this).find('.content-food-cart').text().trim();
            var accessory = $(this).find('.content-accessory-cart').text().trim();
            var quantity = $(this).find('.content-quantity-cart span').text().trim();
            var price = $(this).find('.cart-price-blockcontent span').text().trim();
            var priceTopping = $(this).find('#totalToppingPrice').val().trim();
            data += productName + '-Topping: ' + accessory + '-Số lượng : ' + quantity + '-Tổng tiền: ' + (parseInt(quantity) * parseInt(Number(price.replace(/[^\d.]/g, ''))) + parseInt(priceTopping)) + " \n";


        });
        if(data === ' '){
            alert ('Giỏ hàng trống');
            return;
        }
        var mid = localStorage.getItem('mid');
        if(mid != null || mid != undefined){
            $('#overlay').fadeIn();
            axios.post(' https://ahachat.com/api/bots/12863167/users/' + mid + '/send', {
                ahachat_token: '2eb6146b2cdc6f4903cb44a0749d54336b4d1a7980e9ce3ba5c6d3c2fd537648',
                ahachat_message_tag: 'NON_PROMOTIONAL_SUBSCRIPTION',
                ahachat_block_name: 'block_1',
                DTBmonan: data
            })
                .then((response) => {
                    localStorage.removeItem("mid");
                    axios.get('/api/clearSession');
                    $('#overlay').fadeOut();
                    alert("Thành công");
                    // window.close();
                    location.href='https://m.me/118366466699936?ref=order';
                }, (error) => {
                    $('#overlay').fadeOut();
                    alert("Thất bại");
            });
        }else {
            alert("Bạn phải truy cập web từ bot");
        }

    });


})