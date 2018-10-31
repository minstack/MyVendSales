/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.smyoon.myvendsales;

/**
 *
 * @author SM-MAIN
 */
public class VendRegister {
    
    private String id;
    private String name;
    private String outlet_id;
    private int ask_for_note_on_save;
    private boolean print_not_on_receipt;
    private boolean ask_for_user_on_sale;
    private boolean show_discounts_on_receipts;
    private boolean print_receipt;
    private String receipt_template_id;
    private boolean email_receipt;
    private String invoice_prefix;
    private String invoice_suffix;
    private long invoice_sequence;
    private String button_layout_id;
    private String register_open_time;
    private String register_close_time;
    private String register_open_sequence_id;
    private String deleted_at;
    private long version;
    private boolean is_open;
    private boolean is_quick_keys_enabled;
    private String cash_management_payment_type_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public int getAsk_for_note_on_save() {
        return ask_for_note_on_save;
    }

    public void setAsk_for_note_on_save(int ask_for_note_on_save) {
        this.ask_for_note_on_save = ask_for_note_on_save;
    }

    public boolean isPrint_not_on_receipt() {
        return print_not_on_receipt;
    }

    public void setPrint_not_on_receipt(boolean print_not_on_receipt) {
        this.print_not_on_receipt = print_not_on_receipt;
    }

    public boolean isAsk_for_user_on_sale() {
        return ask_for_user_on_sale;
    }

    public void setAsk_for_user_on_sale(boolean ask_for_user_on_sale) {
        this.ask_for_user_on_sale = ask_for_user_on_sale;
    }

    public boolean isShow_discounts_on_receipts() {
        return show_discounts_on_receipts;
    }

    public void setShow_discounts_on_receipts(boolean show_discounts_on_receipts) {
        this.show_discounts_on_receipts = show_discounts_on_receipts;
    }

    public boolean isPrint_receipt() {
        return print_receipt;
    }

    public void setPrint_receipt(boolean print_receipt) {
        this.print_receipt = print_receipt;
    }

    public String getReceipt_template_id() {
        return receipt_template_id;
    }

    public void setReceipt_template_id(String receipt_template_id) {
        this.receipt_template_id = receipt_template_id;
    }

    public boolean isEmail_receipt() {
        return email_receipt;
    }

    public void setEmail_receipt(boolean email_receipt) {
        this.email_receipt = email_receipt;
    }

    public String getInvoice_prefix() {
        return invoice_prefix;
    }

    public void setInvoice_prefix(String invoice_prefix) {
        this.invoice_prefix = invoice_prefix;
    }

    public String getInvoice_suffix() {
        return invoice_suffix;
    }

    public void setInvoice_suffix(String invoice_suffix) {
        this.invoice_suffix = invoice_suffix;
    }

    public long getInvoice_sequence() {
        return invoice_sequence;
    }

    public void setInvoice_sequence(long invoice_sequence) {
        this.invoice_sequence = invoice_sequence;
    }

    public String getButton_layout_id() {
        return button_layout_id;
    }

    public void setButton_layout_id(String button_layout_id) {
        this.button_layout_id = button_layout_id;
    }

    public String getRegister_open_time() {
        return register_open_time;
    }

    public void setRegister_open_time(String register_open_time) {
        this.register_open_time = register_open_time;
    }

    public String getRegister_close_time() {
        return register_close_time;
    }

    public void setRegister_close_time(String register_close_time) {
        this.register_close_time = register_close_time;
    }

    public String getRegister_open_sequence_id() {
        return register_open_sequence_id;
    }

    public void setRegister_open_sequence_id(String register_open_sequence_id) {
        this.register_open_sequence_id = register_open_sequence_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public boolean isIs_quick_keys_enabled() {
        return is_quick_keys_enabled;
    }

    public void setIs_quick_keys_enabled(boolean is_quick_keys_enabled) {
        this.is_quick_keys_enabled = is_quick_keys_enabled;
    }

    public String getCash_management_payment_type_id() {
        return cash_management_payment_type_id;
    }

    public void setCash_management_payment_type_id(String cash_management_payment_type_id) {
        this.cash_management_payment_type_id = cash_management_payment_type_id;
    }
    
    
}
