package router.kalu.core.interfaces;

import java.util.Map;

public interface IRouteRoot {
    void loadInto(Map<String, Class<? extends IRouteGroup>> routers);
}
