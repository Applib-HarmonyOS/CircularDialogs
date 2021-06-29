package com.example.hassanusman.circulardialogs;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.ResourceTable;
import com.example.circulardialog.extras.CDConstants;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.colors.RgbColor;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CDialogOhosTest {
    private final Context context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    private final CDialog cDialog = new CDialog(context);

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.example.hassanusman.circulardialogs", actualBundleName);
    }

    @Test
    public void testSize() {
        cDialog.setSize(500, 700);
        assertEquals(500, cDialog.getWidth());
        assertEquals(700, cDialog.getHeight());
    }

    @Test
    public void testContentText() {
        cDialog.setContentText("Test");
        assertEquals("Test", cDialog.getMessageText().getText());
    }

    @Test
    public void testTextSize() {
        cDialog.setTextSize(CDConstants.LARGE_TEXT_SIZE);
        int textSize = 0;
        try {
            textSize = context.getResourceManager().getElement(ResourceTable.Integer_large_text).getInteger();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }

        assertEquals(textSize, cDialog.getMessageText().getTextSize());
    }

    @Test
    public void testAnimationDuration() {
        cDialog.setAnimationDuration(2000);
        assertEquals(2000, cDialog.getAnimationDuration());
    }

    @Test
    public void testBackgroundColor() {
        RgbColor color = new RgbColor(255, 165, 0);
        cDialog.setBackgroundColor(color);
        assertEquals(color, cDialog.getBackgroundColor());
    }

    @Test
    public void testAnimationSlide() {
        cDialog.setAnimation(CDConstants.SLIDE_FROM_LEFT_TO_RIGHT);
        assertEquals(CDConstants.SLIDE_FROM_LEFT_TO_RIGHT, cDialog.getAnimation());
    }

    @Test
    public void testAnimationSlide2() {
        cDialog.setAnimation(CDConstants.SLIDE_FROM_TOP_TO_BOTTOM);
        assertEquals(CDConstants.SLIDE_FROM_TOP_TO_BOTTOM, cDialog.getAnimation());
    }

    @Test
    public void testAnimationScale() {
        cDialog.setAnimation(CDConstants.SCALE_FROM_CENTER_TO_CENTER);
        assertEquals(CDConstants.SCALE_FROM_CENTER_TO_CENTER, cDialog.getAnimation());
    }

    @Test
    public void testAnimationScale2() {
        cDialog.setAnimation(CDConstants.SCALE_FROM_RIGHT_TO_LEFT);
        assertEquals(CDConstants.SCALE_FROM_RIGHT_TO_LEFT, cDialog.getAnimation());
    }

    @Test
    public void testCreateAlertSize() {
        cDialog.createAlert("Test", CDConstants.SUCCESS, CDConstants.MEDIUM);
        int size = 0;
        try {
            size = context.getResourceManager().getElement(ResourceTable.Integer_medium_dialog).getInteger();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }

        assertEquals(size, cDialog.getWidth());
    }

    @Test
    public void testCreateAlertBackground() {
        cDialog.createAlert("Test", CDConstants.SUCCESS, CDConstants.MEDIUM);
        int color = 0;
        try {
            color = context.getResourceManager().getElement(ResourceTable.Color_colorSuccess).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }

        assertEquals(RgbColor.fromArgbInt(color), cDialog.getBackgroundColor());
    }

    @Test
    public void testCreateAlertImage() {
        cDialog.createAlert("Test", CDConstants.ERROR, CDConstants.MEDIUM);
        assertEquals(110, cDialog.getImage().getPixelMap().getImageInfo().size.height);
    }

    @Test
    public void testSetAlertType() {
        cDialog.setAlertType(CDConstants.ERROR);
        int color = 0;
        try {
            color = context.getResourceManager().getElement(ResourceTable.Color_colorError).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }

        assertEquals(RgbColor.fromArgbInt(color), cDialog.getBackgroundColor());
    }
}