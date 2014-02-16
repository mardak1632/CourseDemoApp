/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bilisimegitim.coursedemo.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class CRMUtil {

    public static void fitTableColumns(JTable table) {

        int margin = 2;

        for (int i = 0; i < table.getColumnCount(); i++) {

            javax.swing.table.DefaultTableColumnModel colModel = (javax.swing.table.DefaultTableColumnModel) table.getColumnModel();

            javax.swing.table.TableColumn col = colModel.getColumn(i);

            int width = 0;

            table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            javax.swing.table.TableCellRenderer renderer = col.getHeaderRenderer();

            if (renderer == null) {

                renderer = table.getTableHeader().getDefaultRenderer();

            }
            java.awt.Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

            width = comp.getPreferredSize().width;

            for (int r = 0; r < table.getRowCount(); r++) {

                renderer = table.getCellRenderer(r, i);

                comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, i), false, false, r, i);

                width = Math.max(width, comp.getPreferredSize().width);

            }
            width += 2 * margin;

            col.setPreferredWidth(width + 5);

        }
    }

    public static DefaultTableModel convertToTableModel(ResultSet rs) throws Exception {

        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {

                return false;

            }
        };

        ResultSetMetaData rsmd = rs.getMetaData();

        int colCount = rsmd.getColumnCount();

        String[] tableColumnsName = new String[colCount];

        for (int i = 0; i < colCount; i++) {

            tableColumnsName[i] = rsmd.getColumnLabel(i + 1);

        }
        model.setColumnIdentifiers(tableColumnsName);

        while (rs.next()) {

            Object[] objects = new Object[colCount];

            for (int i = 0; i < colCount; i++) {

                objects[i] = rs.getObject(i + 1);

            }
            model.addRow(objects);

        }
        return model;

    }

}
