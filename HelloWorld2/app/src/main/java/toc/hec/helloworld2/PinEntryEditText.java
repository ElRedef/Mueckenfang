package toc.hec.helloworld2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.util.AttributeSet;


public class PinEntryEditText extends android.support.v7.widget.AppCompatEditText {

    public static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";

    private float mSpace = 4; //24 dp by default, space between the lines
    private float mCharSize;
    private float mNumChars = 6;
    private float mLineSpacing = 8; //8dp by default, height of the text from our lines


    private float mLineStroke = 1; //1dp by default
    private float mLineStrokeSelected = 2; //2dp by default

    private Paint mRectPaint; //Paint object fuer die Rechtecke um die Zahlen
    private  Paint mTextPaint; //Paint Object fuer die Zahlen

    //die Farben (werden in Init geladen)
    private int colorInput_Text;
    private int colorRectNotSelected;
    private int colorRectSelected;
    private int colorRectSelectedNext;


    public PinEntryEditText(Context context) {
        super(context);
    }

    public PinEntryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinEntryEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        //Umrechnen auf das konkrete Display
        float multi = context.getResources().getDisplayMetrics().density;
        mLineStroke = multi * mLineStroke;
        mLineStrokeSelected = multi * mLineStrokeSelected;
        mSpace = multi * mSpace; //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing; //convert to pixels for our density

        //Farben laden
        colorInput_Text = getResources().getColor(R.color.numberInput_Text);
        colorRectNotSelected = getResources().getColor(R.color.numberInput_RectNotSelected);
        colorRectSelected = getResources().getColor(R.color.numberInput_RectSelected);
        colorRectSelectedNext = getResources().getColor(R.color.numberInput_RectSelectedNext);

        //Einstellungen um das Rechteck zu malen
        mRectPaint = new Paint(getPaint());
        mRectPaint.setStrokeWidth(mLineStroke);
        mRectPaint.setStyle(Paint.Style.STROKE);

        //Einstellungen um den Text zu malen
        mTextPaint = new Paint(getPaint());
        mTextPaint.setColor(colorInput_Text);

        //kein Hintergrund anzeigen
        setBackgroundResource(0);

        //Maximale Anzahl Zeichen
        mNumChars = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 6); //Anzahl zeichen aus XML Datei holen
    }


    //Wenn Element selektiert wird dann das letzte Zeichen auswahlen
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        setSelection(getText().length()); //Cursor an des Ende des Textes setzen
        super.onSelectionChanged(selStart, selEnd);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        int availableWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize = (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }

        //Eckpunkte des Rechtecks berechnen
        int startX = getPaddingLeft(); //hier beginnt das aktuelle Rechteck. Hier wird der Startwert geladen
        int bottom = getHeight() - getPaddingBottom(); //Unterseite
        float textSize = getPaint().getTextSize(); //Größe des Textes
        int top = bottom -(int) textSize+ 1 * (int) mLineSpacing; //Oberkante der Rechtecke

        //Text Width
        Editable text = getText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(getText(), 0, textLength, textWidths);

        //Fuer jedes Rechteck
        for (int i = 0; i < mNumChars; i++) {
            updateColors(i == textLength); //welche Farbe soll es denn haben?
            canvas.drawRoundRect(startX,top,startX + mCharSize,bottom,20,20,mRectPaint); //Rechteck zeichnen

            if (getText().length() > i) { //wenn es eines der Rechteck ist das bereits Text enthaelt
                float middle = startX + mCharSize / 2;
                canvas.drawText(text, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing, mTextPaint);
            }

            //Position naechstes Rechteck
            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }
        }//for
    }


    /**
     * @param next Is the current char the next character to be input?
     */
    private void updateColors(boolean next) {
        if (isFocused()) {
            mRectPaint.setStrokeWidth(mLineStrokeSelected);
            mRectPaint.setColor(colorRectSelected);
            if (next) {
                mRectPaint.setColor(colorRectSelectedNext);
            }
        } else {
            mRectPaint.setStrokeWidth(mLineStroke);
            mRectPaint.setColor(colorRectNotSelected);
        }
    }




}