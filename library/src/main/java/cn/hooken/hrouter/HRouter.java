package cn.hooken.hrouter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Light weight router tool for modularization system's internal calls between modules.
 *
 * Created by Hulk on 2018/1/6.
 */
public final class HRouter {

    private HRouter() {

    }


    public static final Intent getServiceIntentByPackageManager(@NonNull final Context context, @NonNull final Class<?> interfaceClass) {
        final Intent intent = new Intent(interfaceClass.getName().intern());
        ComponentName componentName = null;
        final List<ResolveInfo> matches = context.getPackageManager().queryIntentServices(intent, 0);
        if (matches != null && !matches.isEmpty()) {
            final ResolveInfo resolveInfo = matches.get(0);
            final ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name.intern());
        }
        if (componentName != null) {
            intent.setComponent(componentName);
            return intent;
        }
        return null;
    }

    public static final <T> T getServiceByLoader(@NonNull final Class<T> interfaceClass) {
        final ServiceLoader<T> loader = ServiceLoader.load(interfaceClass);
        final Iterator<T> iterator = loader.iterator();
        if (iterator.hasNext()) {

            return iterator.next();
        }
        return null;
    }

}