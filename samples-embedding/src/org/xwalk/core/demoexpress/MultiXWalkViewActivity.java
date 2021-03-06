// Copyright (c) 2014 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.xwalk.core.demoexpress;

import java.io.IOException;
import java.io.InputStream;

import org.xwalk.core.XWalkView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MultiXWalkViewActivity extends XWalkBaseActivity {

    private XWalkView mXWalkView2;
    private XWalkView mXWalkView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String getAssetsFileContent(AssetManager assetManager, String fileName)
            throws IOException {
        String result = "";
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            result = new String(buffer);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return result;
    }

	@Override
	protected void onXWalkReady() {

        message = new StringBuffer();
        message.append("Test Purpose: \n\n")
        .append("Verifies XWalkView can create multi instance.\n\n")
        .append("Verifies XWalkView can load app from manifest.\n\n")
        .append("Expected Result:\n\n")
        .append("Test passes if app show 'Hello World'.")
        .append("Test passes if app load two pages.");

        setContentView(R.layout.container);
        LinearLayout parent = (LinearLayout) findViewById(R.id.container);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        mXWalkView = new XWalkView(this, this);
        parent.addView(mXWalkView, params);

        mXWalkView2 = new XWalkView(this, this);
        parent.addView(mXWalkView2, params);

        mXWalkView3 = new XWalkView(this, this);
        parent.addView(mXWalkView3, params);
        
        textDes = (TextView) super.findViewById(R.id.description);
        textDes.setText("This sample demonstrates XWalkView can load multi pages at one time. Page1 and Page2 load page directly, Page3 load app from manifest.");

        mXWalkView.load("file:///android_asset/page1.html", null);

        mXWalkView2.load("file:///android_asset/page2.html", null);
        String manifestContent = "";
        try {
            manifestContent = getAssetsFileContent(this.getAssets(), "manifest.json");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mXWalkView3.loadAppFromManifest("file:///android_asset/", manifestContent);
	}
}
