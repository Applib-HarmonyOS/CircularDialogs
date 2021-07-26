package com.example.hassanusman.circulardialogs;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.ResourceTable;
import com.example.circulardialog.extras.CDConstants;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.colors.RgbColor;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Circular Dialog.
 */
public class CDialogOhosTest {
    private final Context context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    private final CDialog cDialog = new CDialog(context);
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "CDialogOhosTest");

    /**
     * Test for bundle name.
     */
    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.example.hassanusman.circulardialogs", actualBundleName);
    }

    /**
     * Test for setSize function.
     */
    @Test
    public void testSize() {
        cDialog.setSize(500, 700);
        assertEquals(500, cDialog.getWidth());
        assertEquals(700, cDialog.getHeight());
    }

    /**
     * Test for setContentText function.
     */
    @Test
    public void testContentText() {
        cDialog.setContentText("Test");
        assertEquals("Test", cDialog.getMessageText().getText());
    }

    /**
     * Test for setTextSize function.
     */
    @Test
    public void testTextSize() {
        cDialog.setTextSize(CDConstants.LARGE_TEXT_SIZE);
        int textSize = 0;
        try {
            textSize = context.getResourceManager().getElement(ResourceTable.Integer_large_text).getInteger();
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }

        assertEquals(textSize, cDialog.getMessageText().getTextSize());
    }

    /**
     * Test for setAnimationDuration function.
     */
    @Test
    public void testAnimationDuration() {
        cDialog.setAnimationDuration(2000);
        assertEquals(2000, cDialog.getAnimationDuration());
    }

    /**
     * Test for setBackgroundColor function.
     */
    @Test
    public void testBackgroundColor() {
        RgbColor color = new RgbColor(255, 165, 0);
        cDialog.setBackgroundColor(color);
        assertEquals(color, cDialog.getBackgroundColor());
    }

    /**
     * Test for setAnimationDuration function.
     */
    @Test
    public void testAnimationSlide() {
        cDialog.setAnimation(CDConstants.SLIDE_FROM_LEFT_TO_RIGHT);
        assertEquals(CDConstants.SLIDE_FROM_LEFT_TO_RIGHT, cDialog.getAnimation());
    }

    /**
     * Test for setAnimationDuration function.
     */
    @Test
    public void testAnimationSlide2() {
        cDialog.setAnimation(CDConstants.SLIDE_FROM_TOP_TO_BOTTOM);
        assertEquals(CDConstants.SLIDE_FROM_TOP_TO_BOTTOM, cDialog.getAnimation());
    }

    /**
     * Test for setAnimation function (SCALE).
     */
    @Test
    public void testAnimationScale() {
        cDialog.setAnimation(CDConstants.SCALE_FROM_CENTER_TO_CENTER);
        assertEquals(CDConstants.SCALE_FROM_CENTER_TO_CENTER, cDialog.getAnimation());
    }

    /**
     * Test for setAnimation function (SLIDE).
     */
    @Test
    public void testAnimationScale2() {
        cDialog.setAnimation(CDConstants.SCALE_FROM_RIGHT_TO_LEFT);
        assertEquals(CDConstants.SCALE_FROM_RIGHT_TO_LEFT, cDialog.getAnimation());
    }

    /**
     * Test for createAlert function.
     */
    @Test
    public void testCreateAlertSize() {
        cDialog.createAlert("Test", CDConstants.SUCCESS, CDConstants.MEDIUM);
        int size = 0;
        try {
            size = context.getResourceManager().getElement(ResourceTable.Integer_medium_dialog).getInteger();
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }

        assertEquals(size, cDialog.getWidth());
    }

    /**
     * Test for createAlert function.
     */
    @Test
    public void testCreateAlertBackground() {
        cDialog.createAlert("Test", CDConstants.SUCCESS, CDConstants.MEDIUM);
        int color = 0;
        try {
            color = context.getResourceManager().getElement(ResourceTable.Color_colorSuccess).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }

        assertEquals(RgbColor.fromArgbInt(color), cDialog.getBackgroundColor());
    }

    /**
     * Test for createAlert function.
     */
    @Test
    public void testCreateAlertImage() {
        cDialog.createAlert("Test", CDConstants.ERROR, CDConstants.MEDIUM);
        assertEquals(110, cDialog.getImage().getPixelMap().getImageInfo().size.height);
    }

    /**
     * Test for setAlertType function.
     */
    @Test
    public void testSetAlertType() {
        cDialog.setAlertType(CDConstants.ERROR);
        int color = 0;
        try {
            color = context.getResourceManager().getElement(ResourceTable.Color_colorError).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }

        assertEquals(RgbColor.fromArgbInt(color), cDialog.getBackgroundColor());
    }
}