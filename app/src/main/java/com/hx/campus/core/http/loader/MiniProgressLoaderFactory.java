

package com.hx.campus.core.http.loader;

import android.content.Context;

import com.xuexiang.xhttp2.subsciber.impl.IProgressLoader;


public class MiniProgressLoaderFactory implements IProgressLoaderFactory {

    @Override
    public IProgressLoader create(Context context) {
        return new MiniLoadingDialogLoader(context);
    }

    @Override
    public IProgressLoader create(Context context, String message) {
        return new MiniLoadingDialogLoader(context, message);
    }
}
