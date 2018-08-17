package router.kalu.core.interfaces;

import router.kalu.annotation.RouterMeta;

import java.util.Map;

public interface IRouteGroup {
    void loadInto(Map<String, RouterMeta> routerMetaMap);
}
