package cn.diffpi.resource.module.order;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.order.model.Order;
import cn.diffpi.resource.module.user.model.UserAddress;
import cn.diffpi.resource.platform.user.model.ClientUser;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;

@API("/order/details")
public class OrderDetailsResource extends ApiResource {
    @GET
    public Order getOrderDetails(Integer id) {
        Order order = Order.dao.findById(id);
        if (order != null) {
            int clientUserId = order.get("user");
            // 客户信息
            ClientUser clientUser = ClientUser.dao.findById(clientUserId);
            order.put("clientUser", clientUser);
            // 客户地址
            UserAddress userAddress = UserAddress.dao.findById(clientUserId);
            order.put("userAddress", userAddress);
        }

        return order;
    }
}
