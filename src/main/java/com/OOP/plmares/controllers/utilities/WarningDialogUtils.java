package com.OOP.plmares.controllers.utilities;

import javax.swing.*;

public class WarningDialogUtils {

    public static void showDuplicateEntryWarning() {
        JOptionPane.showMessageDialog(
                null,
                "Error: Duplicate entry. A record for this already exists.",
                "Duplicate Entry",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void showGenericErrorWarning() {
        JOptionPane.showMessageDialog(
                null,
                "An error occurred.",
                "Error",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void showSuccessDialog() {
        JOptionPane.showMessageDialog(
                null,
                "Action completed successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
