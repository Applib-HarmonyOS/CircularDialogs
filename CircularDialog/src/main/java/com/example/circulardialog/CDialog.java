package com.example.circulardialog;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.service.DisplayManager;
import ohos.agp.window.service.Window;
import ohos.agp.window.service.WindowManager;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.global.resource.WrongTypeException;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;

import java.io.IOException;

import static com.example.circulardialog.extras.CDConstants.*;

public class CDialog extends CommonDialog {
    final Context context;
    final Component component;
    final Component dialog;

    AnimatorProperty enterAnimatorProperty;
    AnimatorProperty exitAnimatorProperty;
    private int animation;
    private int enterAnimation = SCALE_FROM_TOP;
    private int exitAnimation = SCALE_TO_BOTTOM;
    private int animationDuration = 1500;

    int size = DEFAULT_SIZE;
    final Text messageText;
    final Image image;

    public CDialog(Context context) {
        super(context);
        this.context = context;

        setTransparent(true);
        component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_simple_text, null,true);
        dialog = component.findComponentById(ResourceTable.Id_rl);
        messageText = (Text) component.findComponentById(ResourceTable.Id_msg);
        image = (Image) component.findComponentById(ResourceTable.Id_icn);

        super.setContentCustomComponent(component);
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public int getWidth() {
        return dialog.getWidth();
    }

    public int getHeight() {
        return dialog.getHeight();
    }

    public Text getMessageText() {
        return messageText;
    }

    public Image getImage() {
        return image;
    }

    public CDialog createAlert(String message, int alertType, int givenSize) {
        switch(givenSize){
            case MEDIUM:
                try {
                    size = context.getResourceManager().getElement(ResourceTable.Integer_medium_dialog).getInteger();
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;

            case LARGE:
                try {
                    size = context.getResourceManager().getElement(ResourceTable.Integer_large_dialog).getInteger();
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;
        }

        dialog.setComponentSize(size, size);
        messageText.setText(message);

        setAlertType(alertType);

        return this;
    }

    public CDialog createAlert(String message, PixelMap icon, int alertType, int givenSize) {
        switch(givenSize){
            case MEDIUM:
                try {
                    size = context.getResourceManager().getElement(ResourceTable.Integer_medium_dialog).getInteger();
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;

            case LARGE:
                try {
                    size = context.getResourceManager().getElement(ResourceTable.Integer_large_dialog).getInteger();
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;
        }

        dialog.setComponentSize(size, size);
        messageText.setText(message);

        setAlertType(alertType);
        image.setPixelMap(icon);

        return this;
    }

    public CDialog setAlertType(int alertType) {
        messageText.setTextColor(Color.WHITE);
        ShapeElement shapeElement = (ShapeElement) dialog.getBackgroundElement();
        int colorResID = 0;

        switch(alertType){
            case SUCCESS:
                // success icon
                image.setPixelMap(getPixelMap(ResourceTable.Media_success));
                colorResID = ResourceTable.Color_colorSuccess;
                break;

            case WARNING:
                // warning icon
                image.setPixelMap(getPixelMap(ResourceTable.Media_warning));
                colorResID = ResourceTable.Color_colorWarning;
                break;

            case ERROR:
                // error icon
                image.setPixelMap(getPixelMap(ResourceTable.Media_error));
                colorResID = ResourceTable.Color_colorError;
                break;
        }




        try {
            int color = context.getResourceManager().getElement(colorResID).getColor();
            shapeElement.setRgbColor(RgbColor.fromArgbInt(color));
        } catch (IOException | WrongTypeException | NotExistException e) {
            e.printStackTrace();
        }

        return this;
    }

    public RgbColor getBackgroundColor() {
        ShapeElement shapeElement = (ShapeElement) dialog.getBackgroundElement();
        return shapeElement.getRgbColors()[0];
    }

    @Override
    public CDialog setContentText(String text) {
        messageText.setText(text);
        return this;
    }

    @Override
    public CDialog setContentImage(int resId) {
        image.setPixelMap(resId);
        return this;
    }

    @Override
    public CDialog setSize(int width, int height) {
        dialog.setComponentSize(width, height);
        return this;
    }

    public CDialog setTextSize(int textSize) {
        switch (textSize) {
            case NORMAL_TEXT_SIZE:
                try {
                    messageText.setTextSize(context.getResourceManager().getElement(ResourceTable.Integer_normal_text).getInteger());
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;
            case LARGE_TEXT_SIZE:
                try {
                    messageText.setTextSize(context.getResourceManager().getElement(ResourceTable.Integer_large_text).getInteger());
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;
            case EXTRA_LARGE_TEXT_SIZE:
                try {
                    messageText.setTextSize(context.getResourceManager().getElement(ResourceTable.Integer_extra_large_text).getInteger());
                } catch (IOException | NotExistException | WrongTypeException e) {
                    e.printStackTrace();
                }
                break;
        }

        return this;
    }

    private PixelMap getPixelMap(int resID) {
        RawFileEntry assetManager = null;
        try {
            assetManager = context.getResourceManager().getRawFileEntry(context.getResourceManager().getMediaPath(resID));
        } catch (IOException | NotExistException | WrongTypeException e) {
            e.printStackTrace();
        }
        ImageSource.SourceOptions options = new ImageSource.SourceOptions();
        options.formatHint = "image/png";
        ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
        decodingOptions.desiredSize = new Size(110, 110);
        Resource asset = null;
        try {
            assert assetManager != null;
            asset = assetManager.openRawFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageSource source = ImageSource.create(asset, options);
        return source.createPixelmap(decodingOptions);
    }

    public int getAnimation() {
        return animation;
    }

    public CDialog setAnimation(int animation) {
        this.animation = animation;
        switch (animation) {
            case SCALE_FROM_BOTTOM_TO_BOTTOM:
                enterAnimation = SCALE_FROM_BOTTOM;
                exitAnimation = SCALE_TO_BOTTOM;
                break;
            case SCALE_FROM_BOTTOM_TO_TOP:
                enterAnimation = SCALE_FROM_BOTTOM;
                exitAnimation = SCALE_TO_TOP;
                break;
            case SCALE_FROM_TOP_TO_BOTTOM:
                enterAnimation = SCALE_FROM_TOP;
                exitAnimation = SCALE_TO_BOTTOM;
                break;
            case SCALE_FROM_TOP_TO_TOP:
                enterAnimation = SCALE_FROM_TOP;
                exitAnimation = SCALE_TO_TOP;
                break;

            case SLIDE_FROM_BOTTOM_TO_BOTTOM:
                enterAnimation = SLIDE_FROM_BOTTOM;
                exitAnimation = SLIDE_TO_BOTTOM;
                break;
            case SLIDE_FROM_BOTTOM_TO_TOP:
                enterAnimation = SLIDE_FROM_BOTTOM;
                exitAnimation = SLIDE_TO_TOP;
                break;
            case SLIDE_FROM_TOP_TO_BOTTOM:
                enterAnimation = SLIDE_FROM_TOP;
                exitAnimation = SLIDE_TO_BOTTOM;
                break;
            case SLIDE_FROM_TOP_TO_TOP:
                enterAnimation = SLIDE_FROM_TOP;
                exitAnimation = SLIDE_TO_TOP;
                break;

            case SCALE_FROM_LEFT_TO_LEFT:
                enterAnimation = SCALE_FROM_LEFT;
                exitAnimation = SCALE_TO_LEFT;
                break;
            case SCALE_FROM_LEFT_TO_RIGHT:
                enterAnimation = SCALE_FROM_LEFT;
                exitAnimation = SCALE_TO_RIGHT;
                break;
            case SCALE_FROM_RIGHT_TO_LEFT:
                enterAnimation = SCALE_FROM_RIGHT;
                exitAnimation = SCALE_TO_LEFT;
                break;
            case SCALE_FROM_RIGHT_TO_RIGHT:
                enterAnimation = SCALE_FROM_RIGHT;
                exitAnimation = SCALE_TO_RIGHT;
                break;

            case SLIDE_FROM_LEFT_TO_LEFT:
                enterAnimation = SLIDE_FROM_LEFT;
                exitAnimation = SLIDE_TO_LEFT;
                break;
            case SLIDE_FROM_LEFT_TO_RIGHT:
                enterAnimation = SLIDE_FROM_LEFT;
                exitAnimation = SLIDE_TO_RIGHT;
                break;
            case SLIDE_FROM_RIGHT_TO_LEFT:
                enterAnimation = SLIDE_FROM_RIGHT;
                exitAnimation = SLIDE_TO_LEFT;
                break;
            case SLIDE_FROM_RIGHT_TO_RIGHT:
                enterAnimation = SLIDE_FROM_RIGHT;
                exitAnimation = SLIDE_TO_RIGHT;
                break;

            case SCALE_FROM_CENTER_TO_CENTER:
            default:
                enterAnimation = SCALE_FROM_CENTER;
                exitAnimation = SCALE_TO_CENTER;
                break;
        }

        enterAnimatorProperty = dialog.createAnimatorProperty();
        exitAnimatorProperty = dialog.createAnimatorProperty();

        setEnterAnimation();

        enterAnimatorProperty.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {
            }

            @Override
            public void onStop(Animator animator) {
                AnimatorGroup animatorGroup = new AnimatorGroup();
                animatorGroup.runSerially(
                        image.createAnimatorProperty().rotate(-10),
                        image.createAnimatorProperty().rotate(10)
                );

                animatorGroup.setLoopedCount(-1);
                animatorGroup.start();
                setExitAnimation();
            }

            @Override
            public void onCancel(Animator animator) {

            }

            @Override
            public void onEnd(Animator animator) {

            }

            @Override
            public void onPause(Animator animator) {

            }

            @Override
            public void onResume(Animator animator) {

            }
        });

        exitAnimatorProperty.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {

            }

            @Override
            public void onStop(Animator animator) {
                destroy();
            }

            @Override
            public void onCancel(Animator animator) {

            }

            @Override
            public void onEnd(Animator animator) {

            }

            @Override
            public void onPause(Animator animator) {

            }

            @Override
            public void onResume(Animator animator) {

            }
        });

        component.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                enterAnimatorProperty.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
            }
        });

        dialog.setClickedListener(component -> exitAnimatorProperty.start());

        return this;
    }

    private void setEnterAnimation() {
        int centerX = dialog.getWidth() / 2;
        int centerY = dialog.getHeight() / 2;
        int displayWidth = DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes().width;
        int displayHeight = DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes().height;

        switch (enterAnimation) {
            case SCALE_FROM_BOTTOM:
                dialog.setPivot(centerX, 0.5f * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SCALE_FROM_TOP:
                dialog.setPivot(centerX, -0.5f * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SCALE_FROM_LEFT:
                dialog.setPivot(-0.5f * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SCALE_FROM_RIGHT:
                dialog.setPivot(0.5f * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;

            case SLIDE_FROM_BOTTOM:
                dialog.setPivot(centerX, 8 * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SLIDE_FROM_TOP:
                dialog.setPivot(centerX, -8 * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SLIDE_FROM_LEFT:
                dialog.setPivot(-8 *  displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SLIDE_FROM_RIGHT:
                dialog.setPivot(8 * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;

            case SCALE_FROM_CENTER:
            default:
                enterAnimatorProperty.setDuration(animationDuration).scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
        }
    }

    private void setExitAnimation() {
        int centerX = dialog.getWidth() / 2;
        int centerY = dialog.getHeight() / 2;
        int displayWidth = DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes().width;
        int displayHeight = DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes().height;

        switch (exitAnimation) {
            case SCALE_TO_BOTTOM:
                dialog.setPivot(centerX, 0.5f * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
            case SCALE_TO_TOP:
                dialog.setPivot(centerX, -0.5f * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
            case SCALE_TO_LEFT:
                dialog.setPivot(-0.5f * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
            case SCALE_TO_RIGHT:
                dialog.setPivot(0.5f * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;

            case SLIDE_TO_BOTTOM:
                dialog.setPivot(centerX, 8 * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;
            case SLIDE_TO_TOP:
                dialog.setPivot(centerX, -8 * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;
            case SLIDE_TO_LEFT:
                dialog.setPivot(-8 * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;
            case SLIDE_TO_RIGHT:
                dialog.setPivot(8 *  displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;

            case SCALE_TO_CENTER:
            default:
                exitAnimatorProperty.setDuration(animationDuration).scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
        }
    }

    public CDialog setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    @Override
    public CDialog setAlignment(int alignment) {
        DependentLayout.LayoutConfig dialogLayoutConfig = (DependentLayout.LayoutConfig) dialog.getLayoutConfig();

        switch (alignment) {
            case POSITION_BOTTOM:
            case LayoutAlignment.BOTTOM:
                dialogLayoutConfig.removeRule(DependentLayout.LayoutConfig.CENTER_IN_PARENT);
                dialogLayoutConfig.addRule(DependentLayout.LayoutConfig.ALIGN_PARENT_BOTTOM);
                break;
            case POSITION_TOP:
            case LayoutAlignment.TOP:
                dialogLayoutConfig.removeRule(DependentLayout.LayoutConfig.CENTER_IN_PARENT);
                dialogLayoutConfig.addRule(DependentLayout.LayoutConfig.ALIGN_PARENT_TOP);
                break;
        }
        dialog.setLayoutConfig(dialogLayoutConfig);
        return this;
    }

    public CDialog setPosition(int alignment) {
        return setAlignment(alignment);
    }

    public CDialog setBackgroundColor(RgbColor color) {
        ShapeElement shapeElement = (ShapeElement) dialog.getBackgroundElement();
        shapeElement.setRgbColor(color);
        return this;
    }

    public CDialog setBackDimness(Window window, float alpha) {
        if (window.getLayoutConfig().isPresent()) {
            WindowManager.LayoutConfig layoutConfig = window.getLayoutConfig().get();
            float previousAlpha = layoutConfig.alpha;
            layoutConfig.alpha = alpha;
            window.setLayoutConfig(layoutConfig);

            setDestroyedListener(() -> {
                layoutConfig.alpha = previousAlpha;
                window.setLayoutConfig(layoutConfig);
            });
        }

        return this;
    }

}
