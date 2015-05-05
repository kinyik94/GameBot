package graphic;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

public class MyCaretListener implements CaretListener {
	
	private final JLabel caret;
	private final JLabel function;
	private final String functionName;
	
    MyCaretListener(JLabel caret, JLabel function, String functionName)
    {
        this.caret = caret;
        this.function = function;
        this.functionName = functionName;
    }
    
	public void caretUpdate(CaretEvent e) {
		JTextArea a = (JTextArea) e.getSource();
        try{
            int cpos = a.getCaretPosition();

            int y = a.getLineOfOffset(cpos);
            int x = a.getText().substring(a.getLineStartOffset(y), cpos).length();

            caret.setText((y + 1) + " : " + (x + 1));
            function.setText(functionName);;
        }
        catch (BadLocationException ex){
            throw new RuntimeException("Failed to get pixel position of caret", ex);
        }
    } 

}
