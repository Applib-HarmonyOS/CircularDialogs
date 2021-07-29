/*
* Copyright (C) 2020-21 Application Library Engineering Group
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.circulardialog;

import static com.example.circulardialog.extras.CDConstants.DEFAULT_SIZE;
import static com.example.circulardialog.extras.CDConstants.ERROR;
import static com.example.circulardialog.extras.CDConstants.EXTRA_LARGE_TEXT_SIZE;
import static com.example.circulardialog.extras.CDConstants.LARGE;
import static com.example.circulardialog.extras.CDConstants.LARGE_TEXT_SIZE;
import static com.example.circulardialog.extras.CDConstants.MEDIUM;
import static com.example.circulardialog.extras.CDConstants.NORMAL_TEXT_SIZE;
import static com.example.circulardialog.extras.CDConstants.POSITION_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.POSITION_TOP;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_BOTTOM_TO_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_BOTTOM_TO_TOP;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_CENTER;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_CENTER_TO_CENTER;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_LEFT;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_LEFT_TO_LEFT;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_LEFT_TO_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_RIGHT_TO_LEFT;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_RIGHT_TO_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_TOP;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_TOP_TO_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SCALE_FROM_TOP_TO_TOP;
import static com.example.circulardialog.extras.CDConstants.SCALE_TO_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SCALE_TO_CENTER;
import static com.example.circulardialog.extras.CDConstants.SCALE_TO_LEFT;
import static com.example.circulardialog.extras.CDConstants.SCALE_TO_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SCALE_TO_TOP;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_BOTTOM_TO_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_BOTTOM_TO_TOP;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_LEFT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_LEFT_TO_LEFT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_LEFT_TO_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_RIGHT_TO_LEFT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_RIGHT_TO_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_TOP;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_TOP_TO_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SLIDE_FROM_TOP_TO_TOP;
import static com.example.circulardialog.extras.CDConstants.SLIDE_TO_BOTTOM;
import static com.example.circulardialog.extras.CDConstants.SLIDE_TO_LEFT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_TO_RIGHT;
import static com.example.circulardialog.extras.CDConstants.SLIDE_TO_TOP;
import static com.example.circulardialog.extras.CDConstants.SUCCESS;
import static com.example.circulardialog.extras.CDConstants.WARNING;

import java.io.IOException;
import java.util.Optional;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.agp.window.service.Window;
import ohos.agp.window.service.WindowManager;
import ohos.app.Context;
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
 * Creates a circular dialog.
 */
public class CDialog extends CommonDialog {
    final Context context;
    final Component component;
    final Component dialog;
    final Text messageText;
    final Image image;
    AnimatorProperty enterAnimatorProperty;
    AnimatorProperty exitAnimatorProperty;
    int size = DEFAULT_SIZE;
    private int animation;
    private int enterAnimation = SCALE_FROM_TOP;
    private int exitAnimation = SCALE_TO_BOTTOM;
    private int animationDuration = 1500;
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "CDialog");

    /**
     * Constructor used to create an instance of Circular Dialog.
     *
     * @param context Indicates the ohos context.
     */
    public CDialog(Context context) {
        super(context);
        this.context = context;

        setTransparent(true);
        component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_simple_text, null, true);
        dialog = component.findComponentById(ResourceTable.Id_rl);
        messageText = (Text) component.findComponentById(ResourceTable.Id_msg);
        image = (Image) component.findComponentById(ResourceTable.Id_icn);

        super.setContentCustomComponent(component);
    }

    /**
     * Returns the duration set for animation.
     *
     * @return Animation duration (in ms).
     */
    public int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * Set duration for animation.
     *
     * @param animationDuration Indicates the duration for animation (in ms).
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    /**
     * Returns width of the circular dialog.
     *
     * @return Width of the circular dialog, in units of pixels.
     */
    public int getWidth() {
        return dialog.getWidth();
    }

    /**
     * Returns height of the circular dialog.
     *
     * @return Height of the circular dialog, in units of pixels.
     */
    public int getHeight() {
        return dialog.getHeight();
    }

    /**
     * Returns the text for the dialog.
     *
     * @return Text object for the dialog.
     */
    public Text getMessageText() {
        return messageText;
    }

    /**
     * Returns the image for the dialog.
     *
     * @return Image object for the dialog.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Set the internal circular dialog size.
     * @param givenSize Size of alert (MEDIUM/LARGE)
     */
    private void setCircularDialogSize(int givenSize) {
        switch (givenSize) {
            case MEDIUM:
                try {
                    size = context.getResourceManager().getElement(ResourceTable.Integer_medium_dialog).getInteger();
                } catch (IOException | NotExistException | WrongTypeException e) {
                    HiLog.error(LABEL_LOG, e.getMessage());
                }
                break;

            case LARGE:
            default:
                try {
                    size = context.getResourceManager().getElement(ResourceTable.Integer_large_dialog).getInteger();
                } catch (IOException | NotExistException | WrongTypeException e) {
                    HiLog.error(LABEL_LOG, e.getMessage());
                }
                break;
        }
    }

    /**
     * Creates a Alert circular dialog.
     *
     * @param message   Message to be displayed.
     * @param alertType Type of alert (SUCCESS/WARNING/ERROR)
     * @param givenSize Size of alert (MEDIUM/LARGE)
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog createAlert(String message, int alertType, int givenSize) {
        setCircularDialogSize(givenSize);

        dialog.setComponentSize(size, size);
        messageText.setText(message);

        setAlertType(alertType);

        return this;
    }

    /**
     * Creates a Alert circular dialog.
     *
     * @param message   Message to be displayed.
     * @param icon      PixelMap to be displayed.
     * @param alertType Type of alert (SUCCESS/WARNING/ERROR)
     * @param givenSize Size of alert (MEDIUM/LARGE)
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog createAlert(String message, PixelMap icon, int alertType, int givenSize) {
        setCircularDialogSize(givenSize);

        dialog.setComponentSize(size, size);
        messageText.setText(message);

        setAlertType(alertType);
        image.setPixelMap(icon);

        return this;
    }

    /**
     * Sets an alert type for the circular dialog.
     *
     * @param alertType Value for alert type (SUCCESS/WARNING/ERROR).
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog setAlertType(int alertType) {
        messageText.setTextColor(Color.WHITE);
        ShapeElement shapeElement = (ShapeElement) dialog.getBackgroundElement();
        int colorResId;

        switch (alertType) {
            case SUCCESS:
                // success icon
                image.setPixelMap(getPixelMap(ResourceTable.Media_success));
                colorResId = ResourceTable.Color_colorSuccess;
                break;

            case WARNING:
                // warning icon
                image.setPixelMap(getPixelMap(ResourceTable.Media_warning));
                colorResId = ResourceTable.Color_colorWarning;
                break;

            case ERROR:
                // error icon
                image.setPixelMap(getPixelMap(ResourceTable.Media_error));
                colorResId = ResourceTable.Color_colorError;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + alertType);
        }


        try {
            int color = context.getResourceManager().getElement(colorResId).getColor();
            shapeElement.setRgbColor(RgbColor.fromArgbInt(color));
        } catch (IOException | WrongTypeException | NotExistException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }

        return this;
    }

    /**
     * Returns the background color of the circular dialog.
     *
     * @return RgbColor representing the background color.
     */
    public RgbColor getBackgroundColor() {
        ShapeElement shapeElement = (ShapeElement) dialog.getBackgroundElement();
        return shapeElement.getRgbColors()[0];
    }

    /**
     * Sets the background color of the circular dialog.
     *
     * @param color RgbColor representing the background color.
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog setBackgroundColor(RgbColor color) {
        ShapeElement shapeElement = (ShapeElement) dialog.getBackgroundElement();
        shapeElement.setRgbColor(color);
        return this;
    }

    /**
     * Sets the text to be displayed in the content area.
     *
     * @param text Indicates the text information to display in the content area.
     * @return Returns the CDialog object for continuous operations.
     */
    @Override
    public CDialog setContentText(String text) {
        messageText.setText(text);
        return this;
    }

    /**
     * Sets the icon to be displayed in the content area.
     *
     * @param resId Indicates the resource id of the content to be displayed.
     * @return Returns the CDialog object for continuous operations.
     */
    @Override
    public CDialog setContentImage(int resId) {
        image.setPixelMap(resId);
        return this;
    }

    /**
     * Sets the size of the dialog box.
     *
     * @param width  Indicates the expected width of the dialog box.
     * @param height Indicates the expected height of the dialog box.
     * @return Returns the CDialog object for continuous operations.
     */
    @Override
    public CDialog setSize(int width, int height) {
        dialog.setComponentSize(width, height);
        return this;
    }

    /**
     * Sets the text size for the display message.
     *
     * @param textSize Value of text size (NORMAL_TEXT_SIZE/LARGE_TEXT_SIZE/EXTRA_LARGE_TEXT_SIZE).
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog setTextSize(int textSize) {
        switch (textSize) {
            case LARGE_TEXT_SIZE:
                try {
                    messageText.setTextSize(context.getResourceManager()
                            .getElement(ResourceTable.Integer_large_text).getInteger());
                } catch (IOException | NotExistException | WrongTypeException e) {
                    HiLog.error(LABEL_LOG, e.getMessage());
                }
                break;
            case EXTRA_LARGE_TEXT_SIZE:
                try {
                    messageText.setTextSize(context.getResourceManager()
                            .getElement(ResourceTable.Integer_extra_large_text).getInteger());
                } catch (IOException | NotExistException | WrongTypeException e) {
                    HiLog.error(LABEL_LOG, e.getMessage());
                }
                break;
            case NORMAL_TEXT_SIZE:
            default:
                try {
                    messageText.setTextSize(context.getResourceManager()
                            .getElement(ResourceTable.Integer_normal_text).getInteger());
                } catch (IOException | NotExistException | WrongTypeException e) {
                    HiLog.error(LABEL_LOG, e.getMessage());
                }
                break;
        }

        return this;
    }

    /**
     * Construct a PixelMap from a resource ID.
     *
     * @param resId Indicates the resource ID.
     * @return PixelMap image represented by the resource ID.
     */
    private PixelMap getPixelMap(int resId) {
        RawFileEntry assetManager = null;
        try {
            assetManager = context.getResourceManager()
                    .getRawFileEntry(context.getResourceManager().getMediaPath(resId));
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, e.getMessage());
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
            HiLog.error(LABEL_LOG, e.getMessage());
        }
        ImageSource source = ImageSource.create(asset, options);
        return source.createPixelmap(decodingOptions);
    }

    /**
     * Returns the type of animation.
     *
     * @return Type of animation.
     */
    public int getAnimation() {
        return animation;
    }

    /**
     * Sets the animation to be followed by the circular dialog.
     *
     * @param animation Type of animation (e.g. SCALE_FROM_LEFT_TO_RIGHT).
     * @return Returns the CDialog object for continuous operations.
     */
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
                // No action.
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
                // No action.
            }

            @Override
            public void onEnd(Animator animator) {
                // No action.
            }

            @Override
            public void onPause(Animator animator) {
                // No action.
            }

            @Override
            public void onResume(Animator animator) {
                // No action.
            }
        });

        exitAnimatorProperty.setStateChangedListener(new Animator.StateChangedListener() {
            @Override
            public void onStart(Animator animator) {
                // No action.
            }

            @Override
            public void onStop(Animator animator) {
                destroy();
            }


            @Override
            public void onPause(Animator animator) {
                // No action.
            }

            @Override
            public void onResume(Animator animator) {
                // No action.
            }

            @Override
            public void onCancel(Animator animator) {
                // No action.
            }

            @Override
            public void onEnd(Animator animator) {
                // No action.
            }
        });

        component.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                enterAnimatorProperty.start();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
                // No action.
            }
        });

        dialog.setClickedListener(componentClickedListener -> exitAnimatorProperty.start());

        return this;
    }

    /**
     * Sets the type of enter animation.
     */
    private void setEnterAnimation() {
        int centerX = dialog.getWidth() / 2;
        int centerY = dialog.getHeight() / 2;
        int displayWidth = 1000;
        int displayHeight = 1000;
        Optional<Display> defaultDisplay = DisplayManager.getInstance().getDefaultDisplay(context);
        if (defaultDisplay.isPresent()) {
            displayWidth = defaultDisplay.get().getAttributes().width;
            displayHeight = defaultDisplay.get().getAttributes().height;
        }

        switch (enterAnimation) {
            case SCALE_FROM_BOTTOM:
                dialog.setPivot(centerX, 0.5f * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SCALE_FROM_TOP:
                dialog.setPivot(centerX, -0.5f * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SCALE_FROM_LEFT:
                dialog.setPivot(-0.5f * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SCALE_FROM_RIGHT:
                dialog.setPivot(0.5f * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;

            case SLIDE_FROM_BOTTOM:
                dialog.setPivot(centerX, 8f * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SLIDE_FROM_TOP:
                dialog.setPivot(centerX, -8f * displayHeight);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SLIDE_FROM_LEFT:
                dialog.setPivot(-8f * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;
            case SLIDE_FROM_RIGHT:
                dialog.setPivot(8f * displayWidth, centerY);
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.9f).scaleYFrom(0.9f).scaleX(1.0f).scaleY(1.0f);
                break;

            case SCALE_FROM_CENTER:
            default:
                enterAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(0.0f).scaleYFrom(0.0f).scaleX(1.0f).scaleY(1.0f);
                break;
        }
    }

    /**
     * Sets the type of exit animation.
     */
    private void setExitAnimation() {
        int centerX = dialog.getWidth() / 2;
        int centerY = dialog.getHeight() / 2;
        int displayWidth = 1000;
        int displayHeight = 1000;
        Optional<Display> defaultDisplay = DisplayManager.getInstance().getDefaultDisplay(context);
        if (defaultDisplay.isPresent()) {
            displayWidth = defaultDisplay.get().getAttributes().width;
            displayHeight = defaultDisplay.get().getAttributes().height;
        }

        switch (exitAnimation) {
            case SCALE_TO_BOTTOM:
                dialog.setPivot(centerX, 0.5f * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
            case SCALE_TO_TOP:
                dialog.setPivot(centerX, -0.5f * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
            case SCALE_TO_LEFT:
                dialog.setPivot(-0.5f * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
            case SCALE_TO_RIGHT:
                dialog.setPivot(0.5f * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;

            case SLIDE_TO_BOTTOM:
                dialog.setPivot(centerX, 8f * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;
            case SLIDE_TO_TOP:
                dialog.setPivot(centerX, -8f * displayHeight);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;
            case SLIDE_TO_LEFT:
                dialog.setPivot(-8f * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;
            case SLIDE_TO_RIGHT:
                dialog.setPivot(8f * displayWidth, centerY);
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.9f).scaleY(0.9f);
                break;

            case SCALE_TO_CENTER:
            default:
                exitAnimatorProperty.setDuration(animationDuration)
                        .scaleXFrom(1.0f).scaleYFrom(1.0f).scaleX(0.0f).scaleY(0.0f);
                break;
        }
    }

    /**
     * Sets the alignment for the circular dialog.
     *
     * @param alignment Indicates the alignment mode.
     * @return Returns the CDialog object for continuous operations.
     */
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
            default:
                throw new IllegalStateException("Unexpected value: " + alignment);
        }
        dialog.setLayoutConfig(dialogLayoutConfig);
        return this;
    }

    /**
     * Sets the position of the circular dialog.
     *
     * @param alignment Indicates the alignment mode.
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog setPosition(int alignment) {
        return setAlignment(alignment);
    }

    /**
     * Sets the background dimness for the dialog duration.
     *
     * @param window Window object of the screen.
     * @param alpha  Dim value.
     * @return Returns the CDialog object for continuous operations.
     */
    public CDialog setBackDimness(Window window, float alpha) {
        Optional<WindowManager.LayoutConfig> layoutConfigOptional = window.getLayoutConfig();

        if (layoutConfigOptional.isPresent()) {
            WindowManager.LayoutConfig layoutConfig = layoutConfigOptional.get();
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
