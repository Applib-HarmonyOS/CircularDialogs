package com.example.hassanusman.circulardialogs.slice;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.example.hassanusman.circulardialogs.ResourceTable;
import java.io.IOException;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;

/**
 * MainAbilitySlice class for sample app.
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "MainAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button btn2 = (Button) findComponentById(ResourceTable.Id_test2_btn);
        btn2.setClickedListener(component -> new CDialog(MainAbilitySlice.this).show());

        Button btn3 = (Button) findComponentById(ResourceTable.Id_test3_btn);
        btn3.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .createAlert("Success", CDConstants.SUCCESS, CDConstants.LARGE)
                .show());

        Button btn4 = (Button) findComponentById(ResourceTable.Id_test4_btn);
        btn4.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .createAlert("Warning", CDConstants.WARNING, CDConstants.LARGE)
                .show());

        Button btn5 = (Button) findComponentById(ResourceTable.Id_test5_btn);
        btn5.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .createAlert("Error", CDConstants.ERROR, CDConstants.LARGE)
                .show());

        Button btn6 = (Button) findComponentById(ResourceTable.Id_test6_btn);
        btn6.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .createAlert("Scale Animation", CDConstants.SUCCESS, CDConstants.MEDIUM)
                .setAnimation(CDConstants.SCALE_FROM_TOP_TO_BOTTOM)
                .show());

        Button btn7 = (Button) findComponentById(ResourceTable.Id_test7_btn);
        btn7.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .createAlert("Slide Animation", CDConstants.SUCCESS, CDConstants.MEDIUM)
                .setAlertType(CDConstants.ERROR)
                .setAnimation(CDConstants.SLIDE_FROM_LEFT_TO_RIGHT)
                .show());

        Button btn8 = (Button) findComponentById(ResourceTable.Id_test8_btn);
        btn8.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .setContentText("Custom")
                .setTextSize(CDConstants.NORMAL_TEXT_SIZE)
                .setBackgroundColor(new RgbColor(255, 165, 0))
                .setSize(700, 700)
                .setContentImage(ResourceTable.Media_icon)
                .setPosition(CDConstants.POSITION_BOTTOM)
                .setAnimation(CDConstants.SCALE_FROM_CENTER_TO_CENTER)
                .setAnimationDuration(2000)
                .setBackDimness(getWindow(), 0.4f)
                .show());

        Button btn9 = (Button) findComponentById(ResourceTable.Id_test9_btn);
        btn9.setClickedListener(component -> new CDialog(MainAbilitySlice.this)
                .createAlert("Test", getPixelMap(ResourceTable.Media_icon), CDConstants.ERROR, CDConstants.LARGE)
                .setPosition(CDConstants.POSITION_TOP)
                .show());

    }

    private PixelMap getPixelMap(int resId) {
        RawFileEntry assetManager = null;
        try {
            assetManager = getResourceManager().getRawFileEntry(getResourceManager().getMediaPath(resId));
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }
        ImageSource.SourceOptions options = new ImageSource.SourceOptions();
        ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();

        options.formatHint = "image/png";
        decodingOptions.desiredSize = new Size(100, 100);
        Resource asset = null;
        try {
            assert assetManager != null;
            asset = assetManager.openRawFile();
        } catch (IOException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }
        ImageSource source = ImageSource.create(asset, options);
        return source.createPixelmap(decodingOptions);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
