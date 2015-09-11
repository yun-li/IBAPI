/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package apidemo.util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.plaf.metal.MetalCheckBoxUI;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.plaf.metal.MetalLabelUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class NewLookAndFeel extends MetalLookAndFeel {
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        
        Object[] uiDefaults = new Object[] { 
            	"CheckBoxUI", NewCheckUI.class.getName(),
            	"LabelUI", NewLabelUI.class.getName(),
            	"ComboBoxUI", NewComboUI.class.getName(),
    		    "TableUI", NewTableUI.class.getName()            	
        };
        
        table.putDefaults(uiDefaults);
    }

	public static void register() {
		try {
			UIManager.setLookAndFeel( new NewLookAndFeel() );
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public static class NewLabelUI extends MetalLabelUI {
	    private static final NewLabelUI UI = new NewLabelUI();

	    public static ComponentUI createUI(JComponent c) {
	        return UI;
	    }
	    
	    public void installUI(JComponent c) {
	    	super.installUI(c);
	    	c.setFont( c.getFont().deriveFont(0) );
	    }
	}
	
	public static class NewCheckUI extends MetalCheckBoxUI {
	    private static final NewCheckUI UI = new NewCheckUI();

	    public static ComponentUI createUI(JComponent c) {
	        return UI;
	    }
	    
		public void installUI(JComponent c) {
	    	super.installUI(c);
	    	((JCheckBox)c).setBorder( new EmptyBorder( 3, 0, 3, 0) );
	    }
	}

	public static class NewComboUI extends MetalComboBoxUI {
	    public static ComponentUI createUI(JComponent c) {
	        return new NewComboUI();
	    }
	    
	    public void installUI(JComponent c) {
	    	super.installUI(c);
	    	c.setFont( c.getFont().deriveFont(0) );
	    	c.setPreferredSize( new Dimension( c.getPreferredSize().width, 19));
	    }
	}
	
	public static class NewTableUI extends BasicTableUI {
	    public static ComponentUI createUI(JComponent c) {
	        return new NewTableUI();
	    }
		
	    public void installUI(JComponent c) {
	    	super.installUI(c);
	    	
	    	final JTable table = (JTable)c;
	    	table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);
	    	
			TableColumnModel mod = table.getColumnModel();
			for (int iCol = 0; iCol < mod.getColumnCount(); iCol++) {
				TableColumn col = mod.getColumn( iCol);
				col.setPreferredWidth( 40);
			}

	    	final Timer timer = new Timer( 300, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Util.resizeColumns( table);
				}
	    	});
	    	timer.setRepeats( false);
	    	timer.start();
	    	
	    	table.getModel().addTableModelListener( new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					timer.restart();
				}
	    	});
	    }
	}
}
