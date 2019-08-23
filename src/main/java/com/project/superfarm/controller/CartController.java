package com.project.superfarm.controller;


import com.project.superfarm.entity.product.Cart;
import com.project.superfarm.service.CartService;
import com.project.superfarm.util.ExceptionList.UrlNotFountException;
import com.project.superfarm.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
          @apiNote   : 카트  추가,삭제,전체삭제, 수량 수정
          @Url /cart : /add, /delete, /deleteAll, /edit
          @brief     : /add 추가
                     : /delete 삭제
                     : /deleteAll 전체삭제
                     : /edit   수량 수정

 **/
@RequestMapping("/cart")
@RestController
public class CartController {


    @Autowired
    private CartService cartService;

    /**
     *  @apiNote : 고개번호로 카트 상품추가
     *  @Url     : /cart
     *  @see     : java : Cart.java
     *               DB : cart
        @param   :Json
                "cartNum": 2,
                "userNum": 1,
                "productBoardNum": 1,
                "cartProductName": "이것은 쌀인가",
                "cartProductOption1": "1번옵션",
                "cartProductOption2": "2번 옵션",
                "cartProductPrice": 10000.0,
                "cartProductCount": 2,
                "cartProductImg": "a.jpg"

     */
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},
                produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
                            MediaType.APPLICATION_ATOM_XML_VALUE})
    public List<Cart> loadUserCart(@RequestParam(name="userNum") Long userNum, Principal principal){
        System.out.println(principal);

        if(userNum != null || userNum > 0 ){
            return cartService.loadCart(userNum);

        }else
            throw new UrlNotFountException();

    }

    /** Todo : error 발생 규격화 !완료!
     *
     *  @apiNote : 고개번호로 카트 상품추가
     *  @Url     : /cart/add
     *  @see     : java : Cart.java
     *               DB : cart
        @param   :Json
                 "userNum",
                 "productBoardNum",
                 "cartProductName",
                 "cartProductOption1",
                 "cartProductOption2",
                 "cartProductPrice",
                 "cartProductCount",
                 "cartProductImg"

        @return  :Json Cart.java

     */
    @RequestMapping(path="/add",
    method = RequestMethod.POST,
    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
                MediaType.APPLICATION_ATOM_XML_VALUE})
    public Cart addCartProduct(@RequestBody Cart cart) {

        if(ObjectUtils.isEmpty(cart)) {
            Cart cartCheck = cartService.saveCartProduct(cart);

            if (ObjectUtils.isEmpty(cart)) {
                throw new UrlNotFountException();

            }else
                return cartCheck;

        }else

            throw new UrlNotFountException();

    }


    /**
     *
     * @apiNote    : 장바구니 CartNum 으로 장바구니 목록 상품 하나 삭제
     * @Url        : /cart/delete
     * @see        : java : Cart.java
     *                 DB : cart
     * @param      : Long cartNum
     * @return     : void
     *
     */
    @RequestMapping(path="/delete",
    method = RequestMethod.DELETE)
    public Cart deleteCartProduct(@RequestParam(name ="cartNum")Long cartNum){

       if(cartNum !=null || cartNum>0) {
           return cartService.deleteCart(cartNum);

       }else
           throw new UrlNotFountException();

    }

    /**
     * :
     * @apiNote    : 장바구니 userNum 으로 고객장바구니 목록 전체 삭제
     * @Url        :/cart/deleteAll
     * @see        : java : Cart.java
     *                 DB : cart
     * @param      : Long userNum 고객 번호
     * @return      void
     *
     */
    @RequestMapping(path="/deleteAll",
    method = RequestMethod.DELETE)
    public void deleteCartProductAll(@RequestParam(name ="userNum")Long userNum){

        if(userNum !=null || userNum != 0) {
            cartService.deleteAllCart(userNum);

        }else
            throw new UrlNotFountException();

    }


    /**
     *
     * @apiNote    : 장바구니 userNum 으로 고객장바구니 목록 전체 삭제
     * @Url        : /cart/edit
     * @see        : Java Cart.jva
     *               DB: cart
     * @param      : Long cartNum 고객 번호
     *               Integer count 장바구니 상품의 변경된 수량
     * @return       Cart.java
     *
     */
    @RequestMapping(path="/edit",
    method = RequestMethod.PATCH)
    public List<Cart> cartProductEdit(@RequestBody Cart cart){

        if(cart !=null){

            return cartService.productCountUpdate(cart);

        }else
            throw new UrlNotFountException();
    }
}
