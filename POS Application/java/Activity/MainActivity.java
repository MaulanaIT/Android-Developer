package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Adapter.CustomerListAdapter;
import com.example.project.Adapter.DebitListAdapter;
import com.example.project.Adapter.EditInventoryListAdapter;
import com.example.project.Adapter.ExpensesCategoryListAdapter;
import com.example.project.Adapter.ExpensesListAdapter;
import com.example.project.Adapter.GIftCardListAdapter;
import com.example.project.Adapter.InventoryListAdapter;
import com.example.project.Adapter.OpenedBillListAdapter;
import com.example.project.Adapter.OutletListAdapter;
import com.example.project.Adapter.PaymentMethodListAdapter;
import com.example.project.Adapter.PosOutletsListAdapter;
import com.example.project.Adapter.PosLeftProductListAdapter;
import com.example.project.Adapter.ProductCategoryListAdapter;
import com.example.project.Adapter.ProductListAdapter;
import com.example.project.Adapter.ProfitAndLossReportListAdapter;
import com.example.project.Adapter.PurchaseOrderItemListAdapter;
import com.example.project.Adapter.PurchaseOrderListAdapter;
import com.example.project.Adapter.ReturnOrderListAdapter;
import com.example.project.Adapter.SalesListAdapter;
import com.example.project.Adapter.SalesReportListAdapter;
import com.example.project.Adapter.SoldByProductReportListAdapter;
import com.example.project.Adapter.SupplierListAdapter;
import com.example.project.Adapter.UserListAdapter;
import com.example.project.Fragment.AddGiftCardFragment;
import com.example.project.Fragment.CustomerFragment;
import com.example.project.Fragment.DashboardFragment;
import com.example.project.Fragment.DebitFragment;
import com.example.project.Fragment.EditInventoryFragment;
import com.example.project.Fragment.ExpensesCategoryFragment;
import com.example.project.Fragment.ExpensesFragment;
import com.example.project.Fragment.InventoryFragment;
import com.example.project.Fragment.ListGiftCardFragment;
import com.example.project.Fragment.ListProductFragment;
import com.example.project.Fragment.OpenedBillFragment;
import com.example.project.Fragment.OutletsSettingFragment;
import com.example.project.Fragment.PaymentMethodFragment;
import com.example.project.Fragment.PosListOutletsFragment;
import com.example.project.Fragment.ProductCategoryFragment;
import com.example.project.Fragment.ProfitAndLossFragment;
import com.example.project.Fragment.ProfitAndLossReportsFragment;
import com.example.project.Fragment.PurchaseOrderFragment;
import com.example.project.Fragment.AddReturnOrderFragment;
import com.example.project.Fragment.ReturnOrderReportsFragment;
import com.example.project.Fragment.SalesFragment;
import com.example.project.Fragment.SalesReportsFragment;
import com.example.project.Fragment.SoldByProductReportsFragment;
import com.example.project.Fragment.SuppliersSettingFragment;
import com.example.project.Fragment.SystemSettingFragment;
import com.example.project.Fragment.UsersSettingFragment;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListDebit;
import com.example.project.Model.ListEditInventory;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListExpensesCategory;
import com.example.project.Model.ListGiftCard;
import com.example.project.Model.ListInventory;
import com.example.project.Model.ListMonthlyProfitAndLoss;
import com.example.project.Model.ListOpenedBill;
import com.example.project.Model.ListOutlet;
import com.example.project.Model.ListPaymentMethod;
import com.example.project.Model.ListPosOutlets;
import com.example.project.Model.ListProduct;
import com.example.project.Model.ListProductCategory;
import com.example.project.Model.ListPurchaseOrder;
import com.example.project.Model.ListPurchaseOrderItem;
import com.example.project.Model.ListReturnOrder;
import com.example.project.Model.ListSales;
import com.example.project.Model.ListReport;
import com.example.project.Model.ListSupplier;
import com.example.project.Model.ListUser;
import com.example.project.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout
            drawerLayout;

    NavigationView
            navigationView;

    ActionBarDrawerToggle
            drawerToggle;

    Toolbar
            toolbar;

    Menu
            menu;

    String
            time,
            title,
            name, email, mobile,
            cardNumber, value, expiryDate, status,
            expensesNumber, expensesCategory, outlet, amount,
            productCode, productName, quantity, category, productCost, productPrice,
            categoryName,
            outletName, outletAddress, outletContact,
            billDate, billCustomer, billOutlet, billRefNumber, billItem, billTotal, billTax, billPayable,
            debitCustomer,
            reportID, reportOutlet, reportPaymentMethod, reportTotal, reportTax, reportPayable, reportItem, reportCustomer;

    int
            ID;

    Date
            date, DateFrom, DateTo;

    private SimpleDateFormat
            monthFormat = new SimpleDateFormat("MM", Locale.getDefault()),
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    ProgressDialog
            progressDialog;

    RequestQueue
            requestQueue;

    JsonArrayRequest
            jsonArrayRequest;

    StringRequest
            stringRequest;

    ArrayAdapter<String>
            arrayAdapter;

    private Calendar
            calendar = Calendar.getInstance();

    private Date
            currentDate = calendar.getTime();

    static String
            URL,

    IP_ADDRESS = "https://fantastix.id/android",
    UPLOAD_IMAGE = "https//fantastix.id/product_image/",

    URL_DATA_ADD_CUSTOMER = IP_ADDRESS + "/create/addCustomer.php",
            URL_DATA_ADD_BILL = IP_ADDRESS + "/create/addBill.php",
            URL_DATA_ADD_BILLITEM = IP_ADDRESS + "/create/addBillItem.php",
            URL_DATA_ADD_EXPENSESCATEGORY = IP_ADDRESS + "/create/addExpensesCategory.php",
            URL_DATA_ADD_EXPENSES = IP_ADDRESS + "/create/addExpenses.php",
            URL_DATA_ADD_GIFTCARD = IP_ADDRESS + "/create/addGiftCard.php",
            URL_DATA_ADD_OUTLET = IP_ADDRESS + "/create/addOutlets.php",
            URL_DATA_ADD_PAYMENT = IP_ADDRESS + "/create/addPayment.php",
            URL_DATA_ADD_PAYMENTITEM = IP_ADDRESS + "/create/addPaymentItem.php",
            URL_DATA_ADD_PAYMENTMETHOD = IP_ADDRESS + "/create/addPaymentMethod.php",
            URL_DATA_ADD_PRODUCTCATEGORY = IP_ADDRESS + "/create/addProductCategory.php",
            URL_DATA_ADD_PRODUCT = IP_ADDRESS + "/create/addProduc                                              t.php",
            URL_DATA_ADD_PURCHASEORDER = IP_ADDRESS + "/create/addPurchaseOrder.php",
            URL_DATA_ADD_PURCHASEORDERITEM = IP_ADDRESS + "/create/addPurchaseOrderItem.php",
            URL_DATA_ADD_RETURNORDER = IP_ADDRESS + "/create/addReturnOrder.php",
            URL_DATA_ADD_RETURNORDERITEM = IP_ADDRESS + "/create/addReturnOrderItem.php",
            URL_DATA_ADD_SUPPLIER = IP_ADDRESS + "/create/addSuppliers.php",
            URL_DATA_ADD_USER = IP_ADDRESS + "/create/addUsers.php",

    URL_DATA_DELETE_CUSTOMER = IP_ADDRESS + "/delete/deleteCustomerData.php",
            URL_DATA_DELETE_BILL = IP_ADDRESS + "/delete/deleteBillData.php",
            URL_DATA_DELETE_BILLITEM = IP_ADDRESS + "/delete/deleteBillItemData.php",
            URL_DATA_DELETE_EXPENSESCATEGORY = IP_ADDRESS + "/delete/deleteExpensesCategoryData.php",
            URL_DATA_DELETE_EXPENSES = IP_ADDRESS + "/delete/deleteExpensesData.php",
            URL_DATA_DELETE_GIFTCARD = IP_ADDRESS + "/delete/deleteGiftCardData.php",
            URL_DATA_DELETE_OUTLET = IP_ADDRESS + "/delete/deleteOutletData.php",
            URL_DATA_DELETE_PAYMENT = IP_ADDRESS + "/delete/deletePaymentData.php",
            URL_DATA_DELETE_PAYMENTMETHOD = IP_ADDRESS + "/delete/deletePaymentMethodData.php",
            URL_DATA_DELETE_PRODUCTCATEGORY = IP_ADDRESS + "/delete/deleteProductCategoryData.php",
            URL_DATA_DELETE_PRODUCT = IP_ADDRESS + "/delete/deleteProductData.php",
            URL_DATA_DELETE_PURCHASEORDER = IP_ADDRESS + "/delete/deletePurchaseOrderData.php",
            URL_DATA_DELETE_PURCHASEORDERITEM = IP_ADDRESS + "/delete/deletePurchaseOrderItemData.php",
            URL_DATA_DELETE_RETURNORDER = IP_ADDRESS + "/delete/deleteReturnOrderData.php",
            URL_DATA_DELETE_RETURNORDERITEM = IP_ADDRESS + "/delete/deleteReturnOrderItemData.php",
            URL_DATA_DELETE_SUPPLIER = IP_ADDRESS + "/delete/deleteSupplierData.php",
            URL_DATA_DELETE_SALES = IP_ADDRESS + "/delete/deleteSalesData.php",
            URL_DATA_DELETE_USER = IP_ADDRESS + "/delete/deleteUserData.php",

    URL_DATA_READ_CUSTOMER = IP_ADDRESS + "/read/getCustomerData.php",
            URL_DATA_READ_BILL = IP_ADDRESS + "/read/getBillData.php",
            URL_DATA_READ_BILLITEM = IP_ADDRESS + "/read/getBillItemData.php",
            URL_DATA_READ_DEBIT = IP_ADDRESS + "/read/getDebitData.php",
            URL_DATA_READ_GIFTCARD = IP_ADDRESS + "/read/getGiftCardData.php",
            URL_DATA_READ_EXPENSES = IP_ADDRESS + "/read/getExpensesData.php",
            URL_DATA_READ_EXPENSESCATEGORY = IP_ADDRESS + "/read/getExpensesCategoryData.php",
            URL_DATA_READ_INVENTORY = IP_ADDRESS + "/read/getInventoryData.php",
            URL_DATA_READ_MONTHLYPROFITANDLOSS = IP_ADDRESS + "/read/getMonthlyProfitAndLossStatisticData.php",
            URL_DATA_READ_PRODUCTCATEGORY = IP_ADDRESS + "/read/getProductCategoryData.php",
            URL_DATA_READ_PRODUCT = IP_ADDRESS + "/read/getProductData.php",
            URL_DATA_READ_PURCHASEORDER = IP_ADDRESS + "/read/getPurchaseOrderData.php",
            URL_DATA_READ_PURCHASEORDERITEM = IP_ADDRESS + "/read/getPurchaseOrderItemData.php",
            URL_DATA_READ_REPORT = IP_ADDRESS + "/read/getReportData.php",
            URL_DATA_READ_RETURNORDER = IP_ADDRESS + "/read/getReturnOrderData.php",
            URL_DATA_READ_RETURNORDERITEM = IP_ADDRESS + "/read/getReturnOrderItemData.php",
            URL_DATA_READ_SALES = IP_ADDRESS + "/read/getSalesData.php",
            URL_DATA_READ_OUTLET = IP_ADDRESS + "/read/getOutletsData.php",
            URL_DATA_READ_USER = IP_ADDRESS + "/read/getUserData.php",
            URL_DATA_READ_SUPPLIER = IP_ADDRESS + "/read/getSuppliersData.php",
            URL_DATA_READ_ROLE = IP_ADDRESS + "/read/getRoleData.php",
            URL_DATA_READ_PAYMENT = IP_ADDRESS + "/read/getPaymentData.php",
            URL_DATA_READ_PAYMENTMETHOD = IP_ADDRESS + "/read/getPaymentMethodData.php",

    URL_DATA_UPDATE_CUSTOMER = IP_ADDRESS + "/update/updateCustomerData.php",
            URL_DATA_UPDATE_BILL = IP_ADDRESS + "/update/updateBillData.php",
            URL_DATA_UPDATE_BILLITEM = IP_ADDRESS + "/update/updateBillItemData.php",
            URL_DATA_UPDATE_EXPENSESCATEGORY = IP_ADDRESS + "/update/updateExpensesCategoryData.php",
            URL_DATA_UPDATE_EXPENSES = IP_ADDRESS + "/update/updateExpensesData.php",
            URL_DATA_UPDATE_GIFTCARD = IP_ADDRESS + "/update/updateGiftCardData.php",
            URL_DATA_UPDATE_INVENTORY = IP_ADDRESS + "/update/updateInventoryData.php",
            URL_DATA_UPDATE_OUTLET = IP_ADDRESS + "/update/updateOutletsData.php",
            URL_DATA_UPDATE_PAYMENT = IP_ADDRESS + "/update/updatePaymentData.php",
            URL_DATA_UPDATE_PAYMENTMETHOD = IP_ADDRESS + "/update/updatePaymentMethodData.php",
            URL_DATA_UPDATE_PASSWORD = IP_ADDRESS + "/update/updatePasswordData.php",
            URL_DATA_UPDATE_PRODUCTCATEGORY = IP_ADDRESS + "/update/updateProductCategoryData.php",
            URL_DATA_UPDATE_PRODUCT = IP_ADDRESS + "/update/updateProductData.php",
            URL_DATA_UPDATE_RETURNORDER = IP_ADDRESS + "/update/updateReturnOrderData.php",
            URL_DATA_UPDATE_RETURNORDERITEM = IP_ADDRESS + "/update/updateReturnOrderItemData.php",
            URL_DATA_UPDATE_SUPPLIER = IP_ADDRESS + "/update/updateSuppliersData.php",
            URL_DATA_UPDATE_USER = IP_ADDRESS + "/update/updateUserData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("DASHBOARD");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setOverflowIcon(getDrawable(R.drawable.ic_action_user));

        progressDialog = new ProgressDialog(MainActivity.this);
        requestQueue = Volley.newRequestQueue(this);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new DashboardFragment()).commit();

        setupDrawer();
    }

    //    Call Data Spinner Function In Other Fragment or Activity

    public void setSpinner(final String URL, final String columnName, final Spinner spinner) {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<String> arrayList = new ArrayList<>();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String currentItem = "";
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    currentItem = jsonObject.getString(columnName);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                arrayList.add(currentItem);
                            }
                        }

                        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, arrayList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(arrayAdapter);
                        progressDialog.hide();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                    }
                });

        requestQueue.add(stringRequest);
    }

    //    Call Data POS In Other Fragment or Activity

    public void getMySqlPosProduct(final List<ListProduct> listProductsLeft, final List<ListProduct> listProductsMid, final List<ListProduct> listProductsRight,
                                   final RecyclerView.Adapter adapterLeft, final RecyclerView.Adapter adapterMid, final RecyclerView.Adapter adapterRight,
                                   final RecyclerView recyclerViewLeft, final RecyclerView recyclerViewMid, final RecyclerView recyclerViewRight) {

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        getDataPOSProduct(response, listProductsLeft, listProductsMid, listProductsRight, adapterLeft, adapterMid,
                                adapterRight, recyclerViewLeft, recyclerViewMid, recyclerViewRight);
                        progressDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void getMySqlOrderItem(final List<ListPurchaseOrderItem> listPurchaseOrderItems, final RecyclerView.Adapter adapter,
                                  final RecyclerView recyclerView, final EditText inputOne, final Spinner spinnerOne) {

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        getDataPurchaseOrderItem(response, listPurchaseOrderItems, adapter, recyclerView, inputOne, spinnerOne);
                        progressDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void getMySqlReturnOrder(final List<ListReturnOrder> listReturnOrders, final RecyclerView.Adapter adapter,
                                    final RecyclerView recyclerView, final Spinner spinnerOne) {

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        getDataReturnOrder(response, listReturnOrders, adapter, recyclerView, spinnerOne);
                        progressDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    //    Call All Data Function In Other Fragment or Activity

    public void getMysql(final String condition, final RecyclerView.Adapter adapter,
                         final RecyclerView recyclerView, final List<ListCustomer> listCustomers, final List<ListDebit> listDebits,
                         final List<ListGiftCard> listGiftCards, final List<ListExpenses> listExpenses,
                         final List<ListExpensesCategory> listExpensesCategories, final List<ListInventory> listInventories, final List<ListOpenedBill> listOpenedBills,
                         final List<ListPosOutlets> listPosOutlets, final List<ListProductCategory> listProductCategories, final List<ListMonthlyProfitAndLoss> listMonthlyProfitAndLosses,
                         final List<ListProduct> listProducts, final List<ListPurchaseOrder> listPurchaseOrders, final List<ListSales> listSales, final List<ListOutlet> listOutlets,
                         final List<ListUser> listUsers, final List<ListSupplier> listSuppliers, final List<ListPaymentMethod> listPaymentMethods) {

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        switch (condition) {
            case "Data Customer":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_CUSTOMER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataCustomer(response, listCustomers, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Debit":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_DEBIT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataDebit(response, listDebits, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Gift Card":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_GIFTCARD,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataGiftCard(response, listGiftCards, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Expenses":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_EXPENSES,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataExpenses(response, listExpenses, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Expenses Category":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_EXPENSESCATEGORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataExpensesCategory(response, listExpensesCategories, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Inventory":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_INVENTORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataInventory(response, listInventories, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Opened Bill":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_BILL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataOpenedBill(response, listOpenedBills, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Product Category":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCTCATEGORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataProductCategory(response, listProductCategories, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Outlet":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_OUTLET,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataOutlet(response, listOutlets, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data POS Outlet":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_OUTLET,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataPOSOutlet(response, listPosOutlets, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Profit And Loss Statistic":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_MONTHLYPROFITANDLOSS,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataMonthlyProfitAndLoss(response, listMonthlyProfitAndLosses);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Product":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataProduct(response, listProducts, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Purchase Order":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PURCHASEORDER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataPurchaseOrder(response, listPurchaseOrders, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data User":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_USER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataUser(response, listUsers, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Sales":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_SALES,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataSales(response, listSales, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Supplier":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_SUPPLIER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataSupplier(response, listSuppliers, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Payment Method":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PAYMENTMETHOD,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataPaymentMethod(response, listPaymentMethods, adapter, recyclerView);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;
        }

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    //    Call Search Data Function In Other Fragment or Activity

    public void searchMySql(final String condition, final RecyclerView.Adapter adapter,
                            final RecyclerView recyclerView, final List<ListCustomer> listCustomers, final List<ListDebit> listDebits, final List<ListGiftCard> listGiftCards,
                            final List<ListExpenses> listExpenses, final List<ListInventory> listInventories, final List<ListOpenedBill> listOpenedBills,
                            final List<ListProduct> listProducts, final List<ListProductCategory> listProductCategories, final List<ListReport> listReports,
                            final List<ListOutlet> listOutlets, final EditText inputOne, final EditText inputTwo, final EditText inputThree,
                            final Spinner spinnerOne, final Spinner spinnerTwo, final LinearLayout layoutOne) {

        switch (condition) {
            case "Search Customer":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_CUSTOMER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchCustomer(response, listCustomers, adapter, recyclerView, inputOne, inputTwo, inputThree);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Debit":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_DEBIT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchDebit(response, listDebits, adapter, recyclerView, inputOne, inputTwo, inputThree);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Gift Card":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_GIFTCARD,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchGiftCard(response, listGiftCards, adapter, recyclerView, inputOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Expenses":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_EXPENSES,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchExpenses(response, listExpenses, adapter, recyclerView, inputOne, inputTwo, inputThree,
                                        spinnerOne, spinnerTwo);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Inventory":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_INVENTORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchInventory(response, listInventories, adapter, recyclerView, inputOne, inputTwo);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Opened Bill":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_BILL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchOpenedBill(response, listOpenedBills, adapter, recyclerView, inputOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Product":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchProduct(response, listProducts, adapter, recyclerView, inputOne, inputTwo, spinnerOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Product Category":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCTCATEGORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchProductCategory(response, listProductCategories, adapter, recyclerView, inputOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Profit And Loss Report":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_REPORT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchProfitAndLossReport(response, listReports, adapter, recyclerView, inputOne, inputTwo, inputThree, spinnerOne, layoutOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Return Order Report":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_REPORT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchReturnOrderReport(response, listReports, adapter, recyclerView, inputOne, inputTwo, spinnerOne, spinnerTwo);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Sales Report":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_REPORT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchSalesReport(response, listReports, adapter, recyclerView, inputOne, inputTwo, spinnerOne, spinnerTwo);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Outlet":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_OUTLET,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchOutlet(response, listOutlets, adapter, recyclerView, inputOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;

            case "Search Sold By Product":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_REPORT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                searchSoldByProductReport(response, listReports, adapter, recyclerView, inputOne, inputTwo, inputThree, spinnerOne, layoutOne);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                break;
        }

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

//    Call Data By Id Function In Other Fragment or Activity

    public void getMySqlByID(final String condition, final RecyclerView.Adapter adapter, final RecyclerView recyclerView, final List<ListEditInventory> listEditInventories,
                             final int ID, final EditText inputOne, final EditText inputTwo, final EditText inputThree,
                             final EditText inputFour, final EditText inputFive, final EditText inputSix, final TextView textOne, final TextView textTwo, final TextView textThree,
                             final TextView textFour, final TextView textFive, final TextView textSix, final Spinner spinnerOne,
                             final Spinner spinnerTwo, final Spinner spinnerThree, final Button btnOne, final ImageView imageOne) {

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        switch (condition) {
            case "Data Customer":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_CUSTOMER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataCustomerByID(response, ID, inputOne, inputTwo, inputThree);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Debit":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_DEBIT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataDebitByID(response, ID, textOne, textTwo, textThree, textFour, textFive, textSix);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Expenses":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_EXPENSES,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataExpensesByID(response, ID, inputOne, inputTwo, inputThree, inputFour, imageOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Expenses Category":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_EXPENSESCATEGORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataExpensesCategoryByID(response, ID, inputOne, spinnerOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Inventory":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_INVENTORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataInventoryByID(response, listEditInventories, adapter, recyclerView, btnOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Product":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCT,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataProductByID(response, ID, inputOne, inputTwo, inputThree, inputFour, spinnerOne, spinnerTwo);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Product Category":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PRODUCTCATEGORY,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataProductCategoryByID(response, ID, inputOne, spinnerOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Outlet":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_OUTLET,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataOutletByID(response, ID, inputOne, inputTwo, inputThree, inputFour, spinnerOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data User":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_USER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataUserByID(response, ID, inputOne, inputTwo, spinnerOne, spinnerTwo, spinnerThree);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Supplier":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_SUPPLIER,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataSupplierByID(response, ID, inputOne, inputTwo, inputThree, inputFour, inputFive, inputSix, spinnerOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Payment Method":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_PAYMENTMETHOD,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataPaymentMethodByID(response, ID, inputOne, spinnerOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;

            case "Data Gift Card":
                jsonArrayRequest = new JsonArrayRequest(URL_DATA_READ_GIFTCARD,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                getDataGiftCardByID(response, ID, inputOne, inputTwo, inputThree, spinnerOne);
                                progressDialog.hide();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                            }
                        });
                break;
        }

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void getDataPOSProduct(JSONArray jsonArray, List<ListProduct> listProductsLeft, List<ListProduct> listProductsMid,
                                  List<ListProduct> listProductsRight, RecyclerView.Adapter adapterLeft, RecyclerView.Adapter adapterMid,
                                  RecyclerView.Adapter adapterRight, RecyclerView recyclerViewLeft, RecyclerView recyclerViewMid, RecyclerView recyclerViewRight) {

        for (int i = 0; i < jsonArray.length(); i = i + 3) {

            ListProduct currentItem = new ListProduct();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setProductID(jsonObject.getInt("id"));
                currentItem.setProductCode(jsonObject.getString("code"));
                currentItem.setProductName(jsonObject.getString("product"));
                currentItem.setProductCategory(jsonObject.getString("category"));
                currentItem.setProductCost(jsonObject.getString("purchase"));
                currentItem.setProductPrice(jsonObject.getString("retail"));
                currentItem.setProductImage(jsonObject.getString("image"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setProductStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listProductsLeft.add(currentItem);
        }
        adapterLeft = new PosLeftProductListAdapter(listProductsLeft, this);

        for (int i = 1; i < jsonArray.length(); i = i + 3) {

            ListProduct currentItem = new ListProduct();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setProductID(jsonObject.getInt("id"));
                currentItem.setProductCode(jsonObject.getString("code"));
                currentItem.setProductName(jsonObject.getString("product"));
                currentItem.setProductCategory(jsonObject.getString("category"));
                currentItem.setProductCost(jsonObject.getString("purchase"));
                currentItem.setProductPrice(jsonObject.getString("retail"));
                currentItem.setProductImage(jsonObject.getString("image"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setProductStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listProductsMid.add(currentItem);
        }
        adapterMid = new PosLeftProductListAdapter(listProductsMid, this);

        for (int i = 2; i < jsonArray.length(); i = i + 3) {

            ListProduct currentItem = new ListProduct();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setProductID(jsonObject.getInt("id"));
                currentItem.setProductCode(jsonObject.getString("code"));
                currentItem.setProductName(jsonObject.getString("product"));
                currentItem.setProductCategory(jsonObject.getString("category"));
                currentItem.setProductCost(jsonObject.getString("purchase"));
                currentItem.setProductPrice(jsonObject.getString("retail"));
                currentItem.setProductImage(jsonObject.getString("image"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setProductStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listProductsRight.add(currentItem);
        }
        adapterRight = new PosLeftProductListAdapter(listProductsRight, this);

        recyclerViewLeft.setAdapter(adapterLeft);
        recyclerViewMid.setAdapter(adapterMid);
        recyclerViewRight.setAdapter(adapterRight);
    }

    public void getDataPurchaseOrderItem(JSONArray jsonArray, List<ListPurchaseOrderItem> listPurchaseOrderItems, RecyclerView.Adapter adapter,
                                         RecyclerView recyclerView, EditText inputOne, Spinner spinnerOne) {

        String
                name = "",
                code = "";

        int
                quantity,
                cost;

        for (int i = 0; i < jsonArray.length(); i++) {

            ListPurchaseOrderItem currentItem = new ListPurchaseOrderItem();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                name = jsonObject.getString("product");
                code = jsonObject.getString("code");
                quantity = Integer.valueOf(inputOne.getText().toString());
                cost = jsonObject.getInt("purchase") * quantity;

                currentItem.setProductID(jsonObject.getInt("id"));
                currentItem.setProductName(name);
                currentItem.setProductCode(code);
                currentItem.setProductQuantity(quantity);
                currentItem.setProductCost(cost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (name.equals(spinnerOne.getSelectedItem().toString())) {
                listPurchaseOrderItems.add(currentItem);
            }
        }
        adapter = new PurchaseOrderItemListAdapter(listPurchaseOrderItems, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataReturnOrder(JSONArray jsonArray, List<ListReturnOrder> listReturnOrders, RecyclerView.Adapter adapter,
                                   RecyclerView recyclerView, final Spinner spinnerOne) {

        String
                name = "",
                code = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            ListReturnOrder currentItem = new ListReturnOrder();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                name = jsonObject.getString("product");
                code = jsonObject.getString("code");

                currentItem.setProductID(jsonObject.getInt("id"));
                currentItem.setProductName(name);
                currentItem.setProductCode(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listReturnOrders.size() > 0) {
                for (int j = 0; j < listReturnOrders.size(); j++) {
                    if (name.equals(spinnerOne.getSelectedItem().toString()) && !listReturnOrders.get(j).getProductCode().equals(code)) {
                        listReturnOrders.add(currentItem);
                    }
                }
            } else {
                listReturnOrders.add(currentItem);
            }
        }
        adapter = new ReturnOrderListAdapter(listReturnOrders, this);
//
        recyclerView.setAdapter(adapter);
    }

    public void getDataCustomer(JSONArray jsonArray, List<ListCustomer> listCustomers, RecyclerView.Adapter adapter,
                                RecyclerView recyclerView) {

        adapter = new CustomerListAdapter(listCustomers, this);

        listCustomers.clear();
        adapter.notifyItemRangeRemoved(0, listCustomers.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListCustomer currentItem = new ListCustomer();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setCustomerId(jsonObject.getInt("id"));
                currentItem.setCustomerName(jsonObject.getString("customer"));
                currentItem.setCustomerEmail(jsonObject.getString("email"));
                currentItem.setCustomerMobile(jsonObject.getString("mobile"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            listCustomers.add(currentItem);
        }

        recyclerView.setAdapter(adapter);
    }

    public void getDataDebit(JSONArray jsonArray, List<ListDebit> listDebits, RecyclerView.Adapter adapter,
                             RecyclerView recyclerView) {

        double
                paidAmount = 0,
                payable = 0,
                unpaidAmount = 0;

        adapter = new DebitListAdapter(listDebits, this);

        for (int i = 0; i < jsonArray.length(); i++) {

            ListDebit currentItem = new ListDebit();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                paidAmount = Double.valueOf(jsonObject.getString("paid"));
                payable = Double.valueOf(jsonObject.getString("payable"));
                unpaidAmount = payable - paidAmount;

                currentItem.setDebitID(jsonObject.getInt("id"));
                currentItem.setDebitDate(jsonObject.getString("date"));
                currentItem.setDebitOutlet(jsonObject.getString("outlet"));
                currentItem.setDebitCustomer(jsonObject.getString("customer"));
                currentItem.setDebitPayable(String.valueOf(payable));
                currentItem.setDebitUnpaid(String.valueOf(unpaidAmount));
                currentItem.setDebitReturn(jsonObject.getString("return"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            listDebits.add(currentItem);
        }

        recyclerView.setAdapter(adapter);
    }

    public void getDataGiftCard(JSONArray jsonArray, List<ListGiftCard> listGiftCards, RecyclerView.Adapter adapter,
                                RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListGiftCard currentItem = new ListGiftCard();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setGiftCardID(jsonObject.getInt("id"));
                currentItem.setGiftCardNumber(jsonObject.getString("number"));
                currentItem.setGiftCardValue(jsonObject.getString("value"));
                currentItem.setGiftCardExpiryDate(jsonObject.getString("expiry"));
                int statusCode = jsonObject.getInt("status");
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setGiftCardStatus(status);

            } catch (Exception e) {
                e.printStackTrace();
            }
            listGiftCards.add(currentItem);
        }
        adapter = new GIftCardListAdapter(listGiftCards, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataExpenses(JSONArray jsonArray, List<ListExpenses> listExpenses, RecyclerView.Adapter adapter,
                                RecyclerView recyclerView) {

        adapter = new ExpensesListAdapter(listExpenses, this);

        listExpenses.clear();
        adapter.notifyItemRangeRemoved(0, listExpenses.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListExpenses currentItem = new ListExpenses();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setExpensesID(jsonObject.getInt("id"));
                currentItem.setExpensesNumber(jsonObject.getString("number"));
                currentItem.setExpensesCategory(jsonObject.getString("category"));
                currentItem.setExpensesOutlet(jsonObject.getString("outlet"));
                currentItem.setExpensesDate(dateFormat.parse(jsonObject.getString("date")));
                currentItem.setExpensesAmount(jsonObject.getString("amount"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            listExpenses.add(currentItem);
        }

        recyclerView.setAdapter(adapter);
    }

    public void getDataExpensesCategory(JSONArray jsonArray, List<ListExpensesCategory> listExpensesCategories, RecyclerView.Adapter adapter,
                                        RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListExpensesCategory currentItem = new ListExpensesCategory();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setExpensesCategoryID(jsonObject.getInt("id"));
                currentItem.setExpensesCategoryName(jsonObject.getString("category"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setExpensesCategoryStatus(status);

            } catch (Exception e) {
                e.printStackTrace();
            }
            listExpensesCategories.add(currentItem);
        }
        adapter = new ExpensesCategoryListAdapter(listExpensesCategories, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataInventory(JSONArray jsonArray, List<ListInventory> listInventories, RecyclerView.Adapter adapter,
                                 RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListInventory currentItem = new ListInventory();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setInventoryID(jsonObject.getInt("id"));
                currentItem.setInventoryProductCode(jsonObject.getString("code"));
                currentItem.setInventoryName(jsonObject.getString("product"));
                currentItem.setInventoryQuantity(jsonObject.getString("quantity"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            listInventories.add(currentItem);
        }
        adapter = new InventoryListAdapter(listInventories, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataOpenedBill(JSONArray jsonArray, List<ListOpenedBill> listOpenedBills, RecyclerView.Adapter adapter,
                                  RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListOpenedBill currentItem = new ListOpenedBill();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setBillID(jsonObject.getInt("id"));
                currentItem.setBillDate(jsonObject.getString("date"));
                currentItem.setBillCustomer(jsonObject.getString("customer"));
                currentItem.setBillOutlet(jsonObject.getString("outlet"));
                currentItem.setBillRefNumber(jsonObject.getString("ref_number"));
                currentItem.setBillItem(jsonObject.getString("item"));
                currentItem.setBillTotal(jsonObject.getString("total"));
                currentItem.setBillTax(jsonObject.getString("tax"));
                currentItem.setBillPayable(jsonObject.getString("payable"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            listOpenedBills.add(currentItem);
        }
        adapter = new OpenedBillListAdapter(listOpenedBills, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataProduct(JSONArray jsonArray, List<ListProduct> listProducts, RecyclerView.Adapter adapter,
                               RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListProduct currentItem = new ListProduct();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setProductID(jsonObject.getInt("id"));
                currentItem.setProductCode(jsonObject.getString("code"));
                currentItem.setProductName(jsonObject.getString("product"));
                currentItem.setProductCategory(jsonObject.getString("category"));
                currentItem.setProductCost(jsonObject.getString("purchase"));
                currentItem.setProductPrice(jsonObject.getString("retail"));
                currentItem.setProductImage(jsonObject.getString("image"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setProductStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listProducts.add(currentItem);
        }
        adapter = new ProductListAdapter(listProducts, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataProductCategory(JSONArray jsonArray, List<ListProductCategory> listProductCategories, RecyclerView.Adapter adapter,
                                       RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListProductCategory currentItem = new ListProductCategory();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setProductCategoryID(jsonObject.getInt("id"));
                currentItem.setProductCategoryName(jsonObject.getString("category"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setProductCategoryStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listProductCategories.add(currentItem);
        }
        adapter = new ProductCategoryListAdapter(listProductCategories, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataMonthlyProfitAndLoss(JSONArray jsonArray, List<ListMonthlyProfitAndLoss> listMonthlyProfitAndLosses) {

        String
                day = "",
                month = "";

        double
                total,
                tax,
                payable,
                profit;

        listMonthlyProfitAndLosses.clear();

        for (int i = 0; i < jsonArray.length(); i++) {

            ListMonthlyProfitAndLoss currentItem = new ListMonthlyProfitAndLoss();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                time = monthFormat.format(currentDate);

                reportID = jsonObject.getString("id");
                reportOutlet = jsonObject.getString("outlet_id");
                reportTotal = jsonObject.getString("total");
                reportTax = jsonObject.getString("tax");
                reportPayable = jsonObject.getString("payable");
                status = jsonObject.getString("status");

                profit = Double.valueOf(reportTotal);

                day = jsonObject.getString("day");
                month = jsonObject.getString("month");

                currentItem.setStatisticID(Integer.valueOf(reportID));
                currentItem.setStatisticOutlet(reportOutlet);
                currentItem.setStatisticDay(day);
                currentItem.setStatisticProfit(String.valueOf(profit));

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (month.equals(time) && status.equals("1")) {
                for (int j = 0; j < 31; j++) {
                    if (j != Integer.valueOf(day) - 1) {
                        listMonthlyProfitAndLosses.add(null);
                    } else {
                        listMonthlyProfitAndLosses.add(currentItem);
                    }
                }
            }
        }

        if (listMonthlyProfitAndLosses.size() == 0) {
            Toast.makeText(MainActivity.this, "Statistic Failed Loaded", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Refresh Statistic", Toast.LENGTH_LONG).show();
        }
    }

    public void getDataOutlet(JSONArray jsonArray, List<ListOutlet> listOutlets, RecyclerView.Adapter adapter,
                              RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListOutlet currentItem = new ListOutlet();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setOutletID(jsonObject.getInt("id"));
                currentItem.setOutletName(jsonObject.getString("outlet"));
                currentItem.setOutletAddress(jsonObject.getString("address"));
                currentItem.setOutletContactNumber(jsonObject.getString("contact"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setOutletStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listOutlets.add(currentItem);
        }
        adapter = new OutletListAdapter(listOutlets, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataPOSOutlet(JSONArray jsonArray, List<ListPosOutlets> listPosOutlets, RecyclerView.Adapter adapter,
                                 RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListPosOutlets currentItem = new ListPosOutlets();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setOutletID(jsonObject.getInt("id"));
                currentItem.setOutletsName(jsonObject.getString("outlet"));
                currentItem.setOutletsAddress(jsonObject.getString("address"));
                currentItem.setOutletsTelephone(jsonObject.getString("contact"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            listPosOutlets.add(currentItem);
        }
        adapter = new PosOutletsListAdapter(listPosOutlets, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataUser(JSONArray jsonArray, List<ListUser> listUsers, RecyclerView.Adapter adapter,
                            RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListUser currentItem = new ListUser();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setUserID(jsonObject.getInt("id"));
                currentItem.setUserName(jsonObject.getString("user"));
                currentItem.setUserEmail(jsonObject.getString("email"));
                currentItem.setUserRole(jsonObject.getString("role"));
                currentItem.setUserOutlet(jsonObject.getString("outlet"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setUserStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listUsers.add(currentItem);
        }
        if (listUsers.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Empty", Toast.LENGTH_LONG).show();
        }
        adapter = new UserListAdapter(listUsers, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataSupplier(JSONArray jsonArray, List<ListSupplier> listSuppliers, RecyclerView.Adapter adapter,
                                RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListSupplier currentItem = new ListSupplier();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setSupplierID(jsonObject.getInt("id"));
                currentItem.setSupplierName(jsonObject.getString("supplier"));
                currentItem.setSupplierEmail(jsonObject.getString("email"));
                currentItem.setSupplierTelephone(jsonObject.getString("telephone"));
                currentItem.setSupplierFax(jsonObject.getString("fax"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setSupplierStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listSuppliers.add(currentItem);
        }
        adapter = new SupplierListAdapter(listSuppliers, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataPaymentMethod(JSONArray jsonArray, List<ListPaymentMethod> listPaymentMethods, RecyclerView.Adapter adapter,
                                     RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListPaymentMethod currentItem = new ListPaymentMethod();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setPaymentMethodID(jsonObject.getInt("id"));
                currentItem.setPaymentMethodName(jsonObject.getString("payment"));
                int statusCode = jsonObject.getInt("status");
                String status;
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }
                currentItem.setPaymentMethodStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listPaymentMethods.add(currentItem);
        }
        adapter = new PaymentMethodListAdapter(listPaymentMethods, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataPurchaseOrder(JSONArray jsonArray, List<ListPurchaseOrder> listPurchaseOrders, RecyclerView.Adapter adapter,
                                     RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListPurchaseOrder currentItem = new ListPurchaseOrder();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                currentItem.setPurchaseOrderID(jsonObject.getInt("id"));
                currentItem.setPurchaseOrderNumber(jsonObject.getString("number"));
                currentItem.setPurchaseOrderOutlet(jsonObject.getString("outlet"));
                currentItem.setPurchaseOrderSupplier(jsonObject.getString("supplier"));
                currentItem.setPurchaseOrderDate(jsonObject.getString("date"));
                currentItem.setPurchaseOrderStatus(jsonObject.getString("status"));
                currentItem.setPurchaseOrderStatusCode(jsonObject.getInt("status_id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            listPurchaseOrders.add(currentItem);
        }
        adapter = new PurchaseOrderListAdapter(listPurchaseOrders, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataSales(JSONArray jsonArray, List<ListSales> listSales, RecyclerView.Adapter adapter,
                             RecyclerView recyclerView) {

        for (int i = 0; i < jsonArray.length(); i++) {

            ListSales currentItem = new ListSales();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                status = jsonObject.getString("status");

                currentItem.setSalesID(jsonObject.getInt("id"));
                currentItem.setSalesDate(jsonObject.getString("date"));
                currentItem.setSaledCustomer(jsonObject.getString("customer"));
                currentItem.setSalesOutlet(jsonObject.getString("outlet"));
                currentItem.setSalesMethod(jsonObject.getString("payment"));
                currentItem.setSalesItem(jsonObject.getString("item"));
                currentItem.setSalesTotal(jsonObject.getString("total"));
                currentItem.setSalesTax(jsonObject.getString("tax"));
                currentItem.setSalesPayable(jsonObject.getString("payable"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (status.equals("1")) {
                listSales.add(currentItem);
            }
        }
        adapter = new SalesListAdapter(listSales, this);

        recyclerView.setAdapter(adapter);
    }

    public void getDataCustomerByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, EditText inputThree) {

        int getID = 0;

        String
                customerName = "",
                customerEmail = "",
                customerMobile = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                customerName = jsonObject.getString("customer");
                customerEmail = jsonObject.getString("email");
                customerMobile = jsonObject.getString("mobile");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(customerName);
                inputTwo.setText(customerEmail);
                inputThree.setText(customerMobile);
            }
        }
    }

    public void getDataDebitByID(JSONArray jsonArray, int ID, TextView textOne, TextView textTwo, TextView textThree,
                                 TextView textFour, TextView textFive, TextView textSix) {

        int getID = 0;

        String
                debitCustomer = "",
                debitPayableAmount = "",
                debitItem = "",
                debitPaidBy = "",
                debitPaidAmount = "",
                debitReturn = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                debitCustomer = jsonObject.getString("customer");
                debitPayableAmount = jsonObject.getString("payable");
                debitItem = jsonObject.getString("item");
                debitPaidBy = jsonObject.getString("payment");
                debitPaidAmount = jsonObject.getString("paid");
                debitReturn = jsonObject.getString("return");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                textOne.setText(debitCustomer);
                textTwo.setText(debitPayableAmount);
                textThree.setText(debitItem);
                textFour.setText(debitPaidBy);
                textFive.setText(debitPaidAmount);
                textSix.setText(debitReturn);
            }
        }
    }

    public void getDataExpensesByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, EditText inputThree, EditText inputFour, ImageView imageOne) {

        int getID = 0;

        String
                expensesNumber = "",
                expensesDate = "",
                expensesAmount = "",
                expensesReason = "",
                expensesImage = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                expensesNumber = jsonObject.getString("number");
                expensesDate = jsonObject.getString("date");
                expensesAmount = jsonObject.getString("amount");
                expensesReason = jsonObject.getString("reason");
                expensesImage = jsonObject.getString("image");


            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(expensesNumber);
                inputTwo.setText(expensesDate);
                inputThree.setText(expensesReason);
                inputFour.setText(expensesAmount);
                Picasso.get()
                        .load("https://images.pexels.com/photos/949587/pexels-photo-949587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                        .resize(128, 128)
                        .centerCrop()
                        .into(imageOne);
            }
        }
    }

    public void getDataExpensesCategoryByID(JSONArray jsonArray, int ID, EditText inputOne, Spinner spinnerOne) {

        int
                getID = 0,
                expensesCategoryStatus = 0;

        String
                expensesCategoryName = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                expensesCategoryName = jsonObject.getString("category");
                expensesCategoryStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(expensesCategoryName);
                spinnerOne.setSelection(expensesCategoryStatus);
            }
        }
    }

    public void getDataInventoryByID(JSONArray jsonArray, List<ListEditInventory> listEditInventories, RecyclerView.Adapter adapter,
                                     RecyclerView recyclerView, Button btnOne) {

        adapter = new EditInventoryListAdapter(listEditInventories, this);

        int
                inventoryID = 0,
                inventoryQuantity = 0;

        String
                inventoryCode = "",
                inventoryOutlet = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            ListEditInventory currentItem = new ListEditInventory();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                inventoryID = jsonObject.getInt("id");
                inventoryCode = jsonObject.getString("code");
                inventoryOutlet = jsonObject.getString("outlet");
                inventoryQuantity = jsonObject.getInt("quantity");

                currentItem.setInventoryID(inventoryID);
                currentItem.setOutletName(inventoryOutlet);
                currentItem.setInventoryQuantity(inventoryQuantity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (inventoryCode.equals(EditInventoryFragment.productCode)) {
                listEditInventories.add(currentItem);
            }
        }

        if (listEditInventories.size() == 0) {
            Toast.makeText(MainActivity.this, EditInventoryFragment.productCode, Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);
    }

    public void getDataProductByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, EditText inputThree,
                                   EditText inputFour, Spinner spinnerOne, Spinner spinnerTwo) {

        int
                getID = 0,
                productPurchase = 0,
                productRetail = 0,
                productStatus = 0;

        String
                productName = "",
                productCode = "",
                productCategory = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                productCode = jsonObject.getString("code");
                productName = jsonObject.getString("product");
                productPurchase = jsonObject.getInt("purchase");
                productRetail = jsonObject.getInt("retail");
                productCategory = jsonObject.getString("category");
                productStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(productCode);
                inputTwo.setText(productName);
                inputThree.setText(String.valueOf(productPurchase));
                inputFour.setText(String.valueOf(productRetail));
                spinnerOne.setSelection(arrayAdapter.getPosition(productCategory));
                spinnerTwo.setSelection(productStatus);
            }
        }
    }

    public void getDataProductCategoryByID(JSONArray jsonArray, int ID, EditText inputOne, Spinner spinnerOne) {

        int
                getID = 0,
                productCategoryStatus = 0;

        String
                productCategoryName = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                productCategoryName = jsonObject.getString("category");
                productCategoryStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(productCategoryName);
                spinnerOne.setSelection(productCategoryStatus);
            }
        }
    }

    public void getDataOutletByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, EditText inputThree,
                                  EditText inputFour, Spinner spinnerOne) {

        int
                getID = 0,
                outletStatus = 0;

        String
                outletName = "",
                outletContact = "",
                outletAddress = "",
                outletFooter = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                outletName = jsonObject.getString("outlet");
                outletContact = jsonObject.getString("contact");
                outletAddress = jsonObject.getString("address");
                outletFooter = jsonObject.getString("footer");
                outletStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(outletName);
                inputTwo.setText(outletContact);
                inputThree.setText(outletAddress);
                inputFour.setText(outletFooter);
                spinnerOne.setSelection(outletStatus);
            }
        }
    }

    public void getDataUserByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, Spinner spinnerOne, Spinner spinnerTwo, Spinner spinnerThree) {

        int
                getID = 0,
                userStatus = 0;

        String
                userName = "",
                userEmail = "",
                userRole = "",
                userOutlet = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                userName = jsonObject.getString("user");
                userEmail = jsonObject.getString("email");
                userRole = jsonObject.getString("role");
                userOutlet = jsonObject.getString("outlet");
                userStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(userName);
                inputTwo.setText(userEmail);
                spinnerOne.setSelection(arrayAdapter.getPosition(userRole));
                spinnerTwo.setSelection(arrayAdapter.getPosition(userOutlet));
                spinnerThree.setSelection(userStatus);
            }
        }
    }

    public void getDataSupplierByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, EditText inputThree,
                                    EditText inputFour, EditText inputFive, EditText inputSix, Spinner spinnerOne) {

        int
                getID = 0,
                supplierStatus = 0;

        String
                supplierName = "",
                supplierEmail = "",
                supplierTelephone = "",
                supplierFax = "",
                supplierAddress = "",
                supplierTax = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                supplierName = jsonObject.getString("supplier");
                supplierTax = jsonObject.getString("tax");
                supplierEmail = jsonObject.getString("email");
                supplierTelephone = jsonObject.getString("telephone");
                supplierFax = jsonObject.getString("fax");
                supplierAddress = jsonObject.getString("address");
                supplierStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(supplierName);
                inputTwo.setText(supplierEmail);
                inputThree.setText(supplierTelephone);
                inputFour.setText(supplierFax);
                inputFive.setText(supplierAddress);
                inputSix.setText(supplierTax);
                spinnerOne.setSelection(supplierStatus);
            }
        }
    }

    public void getDataPaymentMethodByID(JSONArray jsonArray, int ID, EditText inputOne, Spinner spinnerOne) {

        int
                getID = 0,
                paymentMethodStatus = 0;

        String
                paymentMethodName = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                paymentMethodName = jsonObject.getString("payment");
                paymentMethodStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(paymentMethodName);
                spinnerOne.setSelection(paymentMethodStatus);
            }
        }
    }

    public void getDataGiftCardByID(JSONArray jsonArray, int ID, EditText inputOne, EditText inputTwo, EditText inputThree, Spinner spinnerOne) {

        int
                getID = 0,
                giftCardValue = 0,
                giftCardStatus = 0;

        String
                giftCardNumber = "",
                giftCardExpireDate = "";

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                getID = jsonObject.getInt("id");
                giftCardNumber = jsonObject.getString("number");
                giftCardValue = jsonObject.getInt("value");
                giftCardExpireDate = jsonObject.getString("expiry");
                giftCardStatus = jsonObject.getInt("status");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (getID == ID) {
                inputOne.setText(giftCardNumber);
                inputTwo.setText(String.valueOf(giftCardValue));
                inputThree.setText(giftCardExpireDate);
                spinnerOne.setSelection(giftCardStatus);
            }
        }
    }

    public void searchCustomer(JSONArray jsonArray, List<ListCustomer> listCustomers, RecyclerView.Adapter adapter,
                               RecyclerView recyclerView, EditText inputName, EditText inputEmail, EditText inputMobile) {

        final String
                inputNameString = inputName.getText().toString().trim(),
                inputEmailString = inputEmail.getText().toString().trim(),
                inputMobileString = inputMobile.getText().toString().trim();

        adapter = new CustomerListAdapter(listCustomers, this);

        listCustomers.clear();
        adapter.notifyItemRangeRemoved(0, listCustomers.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListCustomer currentItem = new ListCustomer();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                name = jsonObject.getString("customer");
                email = jsonObject.getString("email");
                mobile = jsonObject.getString("mobile");

                currentItem.setCustomerName(name);
                currentItem.setCustomerEmail(email);
                currentItem.setCustomerMobile(mobile);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (name.contains(inputNameString) && email.contains(inputEmailString) && mobile.contains(inputMobileString)) {
                listCustomers.add(currentItem);
            }
        }

        if (listCustomers.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchDebit(JSONArray jsonArray, List<ListDebit> listDebits, RecyclerView.Adapter adapter,
                               RecyclerView recyclerView, EditText inputOne, EditText inputTwo, EditText inputThree) {

        double
                paidAmount,
                payable,
                unpaidAmount;

        final String
                stringCustomer = inputOne.getText().toString().trim(),
                stringDateFrom = inputTwo.getText().toString().trim(),
                stringDateTo = inputThree.getText().toString().trim();

        try {
            DateFrom = dateFormat.parse(stringDateFrom);
            DateTo = dateFormat.parse(stringDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new DebitListAdapter(listDebits, this);

        listDebits.clear();
        adapter.notifyItemRangeRemoved(0, listDebits.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListDebit currentItem = new ListDebit();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                paidAmount = Double.valueOf(jsonObject.getString("paid"));
                payable = Double.valueOf(jsonObject.getString("payable"));
                unpaidAmount = payable - paidAmount;

                debitCustomer = jsonObject.getString("customer");
                date = dateFormat.parse(jsonObject.getString("date"));

                currentItem.setDebitID(jsonObject.getInt("id"));
                currentItem.setDebitDate(jsonObject.getString("date"));
                currentItem.setDebitOutlet(jsonObject.getString("outlet"));
                currentItem.setDebitPayable(String.valueOf(payable));
                currentItem.setDebitUnpaid(String.valueOf(unpaidAmount));
                currentItem.setDebitReturn(jsonObject.getString("return"));
                currentItem.setDebitCustomer(debitCustomer);
                currentItem.setDate(date);

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean
                    conditionDate = (date.equals(DateFrom) || date.after(DateFrom)) && (date.before(DateTo) || date.equals(DateTo));

            if (debitCustomer.contains(stringCustomer) && conditionDate) {
                listDebits.add(currentItem);
            }
        }

        if (listDebits.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchGiftCard(JSONArray jsonArray, List<ListGiftCard> listGiftCards, RecyclerView.Adapter adapter,
                               RecyclerView recyclerView, EditText inputSearch) {

        adapter = new GIftCardListAdapter(listGiftCards, this);

        listGiftCards.clear();
        adapter.notifyItemRangeRemoved(0, listGiftCards.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListGiftCard currentItem = new ListGiftCard();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                cardNumber = jsonObject.getString("number");
                value = jsonObject.getString("value");
                expiryDate = jsonObject.getString("expiry");

                int statusCode = jsonObject.getInt("status");
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }

                currentItem.setGiftCardNumber(cardNumber);
                currentItem.setGiftCardValue(value);
                currentItem.setGiftCardExpiryDate(expiryDate);
                currentItem.setGiftCardStatus(status);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (cardNumber.contains(inputSearch.getText().toString().trim()) || value.contains(inputSearch.getText().toString().trim()) ||
                    expiryDate.contains(inputSearch.getText().toString().trim())) {
                listGiftCards.add(currentItem);
            } else if (inputSearch.getText().toString().equals("")) {
                getMysql("Data Gift Card", adapter, recyclerView,
                        null, null, listGiftCards, null, null, null, null,
                        null, null, null, null, null, null,
                        null, null, null, null);
            }
        }
        if (listGiftCards.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);
    }

    public void searchExpenses(JSONArray jsonArray, List<ListExpenses> listExpenses, RecyclerView.Adapter adapter,
                               RecyclerView recyclerView, EditText inputNumber, EditText inputDateFrom, EditText inputDateTo,
                               Spinner spinnerCategory, Spinner spinnerOutlet) {

        final String
                stringNumber = inputNumber.getText().toString().trim(),
                stringCategory = spinnerCategory.getSelectedItem().toString().trim(),
                stringOutlet = spinnerOutlet.getSelectedItem().toString().trim(),
                stringDateFrom = inputDateFrom.getText().toString().trim(),
                stringDateTo = inputDateTo.getText().toString().trim();

        try {
            DateFrom = dateFormat.parse(stringDateFrom);
            DateTo = dateFormat.parse(stringDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new ExpensesListAdapter(listExpenses, this);

        listExpenses.clear();
        adapter.notifyItemRangeRemoved(0, listExpenses.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListExpenses currentItem = new ListExpenses();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                expensesNumber = jsonObject.getString("number");
                expensesCategory = jsonObject.getString("category");
                outlet = jsonObject.getString("outlet");
                date = dateFormat.parse(jsonObject.getString("date"));
                amount = jsonObject.getString("amount");

                currentItem.setExpensesNumber(expensesNumber);
                currentItem.setExpensesCategory(expensesCategory);
                currentItem.setExpensesOutlet(outlet);
                currentItem.setExpensesDate(date);
                currentItem.setExpensesAmount(amount);

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean
                    conditionCategoryOutlet = expensesCategory.equals(stringCategory) && outlet.equals(stringOutlet),
                    conditionDate = (date.equals(DateFrom) || date.after(DateFrom)) && (date.before(DateTo) || date.equals(DateTo));

            if (expensesNumber.contains(stringNumber) && conditionCategoryOutlet && conditionDate) {
                listExpenses.add(currentItem);
            }
        }

        if (listExpenses.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchInventory(JSONArray jsonArray, List<ListInventory> listInventories, RecyclerView.Adapter adapter,
                                RecyclerView recyclerView, EditText inputProductCode, EditText inputProductName) {

        final String
                stringProductCode = inputProductCode.getText().toString().trim(),
                stringProductName = inputProductName.getText().toString().trim();

        adapter = new InventoryListAdapter(listInventories, this);

        listInventories.clear();
        adapter.notifyItemRangeRemoved(0, listInventories.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListInventory currentItem = new ListInventory();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                productCode = jsonObject.getString("code");
                productName = jsonObject.getString("product");
                quantity = jsonObject.getString("quantity");

                currentItem.setInventoryProductCode(productCode);
                currentItem.setInventoryName(productName);
                currentItem.setInventoryQuantity(quantity);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (productCode.contains(stringProductCode) && productName.contains(stringProductName)) {
                listInventories.add(currentItem);
            }
        }
        if (listInventories.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);
    }

    public void searchOpenedBill(JSONArray jsonArray, List<ListOpenedBill> listOpenedBills, RecyclerView.Adapter adapter,
                                RecyclerView recyclerView, EditText inputOne) {

        final String
                stringSearchBill = inputOne.getText().toString().trim();

        adapter = new OpenedBillListAdapter(listOpenedBills, this);

        listOpenedBills.clear();
        adapter.notifyItemRangeRemoved(0, listOpenedBills.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListOpenedBill currentItem = new ListOpenedBill();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                billDate = jsonObject.getString("date");
                billCustomer = jsonObject.getString("customer");
                billOutlet = jsonObject.getString("outlet");
                billRefNumber = jsonObject.getString("ref_number");
                billItem = jsonObject.getString("item");
                billTotal = jsonObject.getString("total");
                billTax = jsonObject.getString("tax");
                billPayable = jsonObject.getString("payable");

                currentItem.setBillDate(billDate);
                currentItem.setBillCustomer(billCustomer);
                currentItem.setBillOutlet(billOutlet);
                currentItem.setBillRefNumber(billRefNumber);
                currentItem.setBillItem(billItem);
                currentItem.setBillTotal(billTotal);
                currentItem.setBillTax(billTax);
                currentItem.setBillPayable(billPayable);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (billDate.contains(stringSearchBill) || billCustomer.contains(stringSearchBill) || billOutlet.contains(stringSearchBill) ||
                    billRefNumber.contains(stringSearchBill) || billItem.contains(stringSearchBill) || billTotal.contains(stringSearchBill) ||
                    billTax.contains(stringSearchBill) || billPayable.contains(stringSearchBill)) {
                listOpenedBills.add(currentItem);
            }
        }
        if (listOpenedBills.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);
    }

    public void searchProduct(JSONArray jsonArray, List<ListProduct> listProducts, RecyclerView.Adapter adapter,
                              RecyclerView recyclerView, EditText inputProductCode, EditText inputProductName, Spinner inputProductCategory) {

        final String
                stringProductCode = inputProductCode.getText().toString().trim(),
                stringProductName = inputProductName.getText().toString().trim(),
                stringProductCategory = inputProductCategory.getSelectedItem().toString().trim();

        adapter = new ProductListAdapter(listProducts, this);

        listProducts.clear();
        adapter.notifyItemRangeRemoved(0, listProducts.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListProduct currentItem = new ListProduct();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                productCode = jsonObject.getString("code");
                productName = jsonObject.getString("product");
                category = jsonObject.getString("category");
                productCost = jsonObject.getString("purchase");
                productPrice = jsonObject.getString("retail");
                currentItem.setProductImage(jsonObject.getString("image"));

                int statusCode = jsonObject.getInt("status");
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }

                currentItem.setProductCode(productCode);
                currentItem.setProductName(productName);
                currentItem.setProductCategory(category);
                currentItem.setProductCost(productCost);
                currentItem.setProductPrice(productPrice);
                currentItem.setProductStatus(status);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (productCode.contains(stringProductCode) && productName.contains(stringProductName) && category.equals(stringProductCategory)) {
                listProducts.add(currentItem);
            }
        }

        if (listProducts.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchProductCategory(JSONArray jsonArray, List<ListProductCategory> listProductCategories, RecyclerView.Adapter adapter,
                                      RecyclerView recyclerView, EditText inputSearch) {

        adapter = new ProductCategoryListAdapter(listProductCategories, this);

        listProductCategories.clear();
        adapter.notifyItemRangeRemoved(0, listProductCategories.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListProductCategory currentItem = new ListProductCategory();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                categoryName = jsonObject.getString("category");

                int statusCode = jsonObject.getInt("status");
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }

                currentItem.setProductCategoryName(categoryName);
                currentItem.setProductCategoryStatus(status);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (categoryName.contains(inputSearch.getText().toString().trim()) || status.contains(inputSearch.getText().toString().trim())) {
                listProductCategories.add(currentItem);
            } else if (inputSearch.getText().toString().equals("")) {
                getMysql("Data Product Category", adapter, recyclerView,
                        null, null, null, null, null, null, null, null,
                        listProductCategories, null, null, null, null, null,
                        null, null, null);
            }
        }

        if (listProductCategories.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchOutlet(JSONArray jsonArray, List<ListOutlet> listOutlets, RecyclerView.Adapter adapter,
                             RecyclerView recyclerView, EditText inputSearch) {

        adapter = new OutletListAdapter(listOutlets, this);

        listOutlets.clear();
        adapter.notifyItemRangeRemoved(0, listOutlets.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListOutlet currentItem = new ListOutlet();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                outletName = jsonObject.getString("outlet");
                outletAddress = jsonObject.getString("address");
                outletContact = jsonObject.getString("contact");

                int statusCode = jsonObject.getInt("status");
                if (statusCode == 1) {
                    status = "Active";
                } else {
                    status = "Not Active";
                }

                currentItem.setOutletName(outletName);
                currentItem.setOutletAddress(outletAddress);
                currentItem.setOutletContactNumber(outletContact);
                currentItem.setOutletStatus(status);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (outletName.contains(inputSearch.getText().toString().trim()) || outletAddress.contains(inputSearch.getText().toString().trim()) ||
                    outletContact.contains(inputSearch.getText().toString().trim()) || status.contains(inputSearch.getText().toString().trim())) {
                listOutlets.add(currentItem);
            } else if (inputSearch.getText().toString().trim().equals("")) {
                getMysql("Data Outlet", adapter, recyclerView,
                        null, null, null, null, null, null, null, null,
                        null, null, null, null, null, listOutlets,
                        null, null, null);
            }
        }

        if (listOutlets.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchProfitAndLossReport(JSONArray jsonArray, List<ListReport> listReports, RecyclerView.Adapter adapter,
                                          RecyclerView recyclerView, EditText inputOne, EditText inputTwo, EditText inputThree, Spinner spinnerOne, LinearLayout layoutOne) {

        final String
                stringSearch = inputThree.getText().toString().trim(),
                stringDateFrom = inputOne.getText().toString().trim(),
                stringDateTo = inputTwo.getText().toString().trim();

        try {
            DateFrom = dateFormat.parse(stringDateFrom);
            DateTo = dateFormat.parse(stringDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new ProfitAndLossReportListAdapter(listReports, this);

        listReports.clear();
        adapter.notifyItemRangeRemoved(0, listReports.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListReport currentItem = new ListReport();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                reportID = jsonObject.getString("id");
                reportOutlet = jsonObject.getString("outlet");
                reportPaymentMethod = jsonObject.getString("payment");
                reportTotal = jsonObject.getString("total");
                reportTax = jsonObject.getString("tax");
                reportPayable = jsonObject.getString("payable");
                status = jsonObject.getString("status");

                date = dateFormat.parse(jsonObject.getString("date"));

                currentItem.setReportID(Integer.valueOf(reportID));
                currentItem.setReportOutlet(reportOutlet);
                currentItem.setReportPaymentMethod(reportPaymentMethod);
                currentItem.setReportTotal(reportTotal);
                currentItem.setReportTax(reportTax);
                currentItem.setReportPayable(reportPayable);
                currentItem.setReportDate(date);

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean
                    conditionDate = (date.equals(DateFrom) || date.after(DateFrom)) && (date.before(DateTo) || date.equals(DateTo));

            if (inputThree.getText().toString().isEmpty()) {
                if (reportOutlet.equals(spinnerOne.getSelectedItem().toString()) && conditionDate && status.equals("1")) {
                    listReports.add(currentItem);
                }
            } else {
                if ((reportID.contains(stringSearch) || reportOutlet.contains(stringSearch) || reportPaymentMethod.contains(stringSearch) ||
                        reportTotal.contains(stringSearch) || reportTax.contains(stringSearch) || reportPayable.contains(stringSearch)) &&
                        conditionDate && status.equals("1")) {
                    listReports.add(currentItem);
                }
            }
        }

        if (layoutOne.getVisibility() == View.GONE) {
            if (listReports.size() == 0) {
                Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
            }  else {
                layoutOne.setVisibility(View.VISIBLE);
            }
        } else {
            if (listReports.size() == 0) {
                Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
            }
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchReturnOrderReport(JSONArray jsonArray, List<ListReport> listReports, RecyclerView.Adapter adapter,
                                        RecyclerView recyclerView, EditText inputOne, EditText inputTwo, Spinner spinnerOne, Spinner spinnerTwo) {

        final String
                stringDateFrom = inputOne.getText().toString().trim(),
                stringDateTo = inputTwo.getText().toString().trim();

        try {
            DateFrom = dateFormat.parse(stringDateFrom);
            DateTo = dateFormat.parse(stringDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new SalesReportListAdapter(listReports, this);

        listReports.clear();
        adapter.notifyItemRangeRemoved(0, listReports.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListReport currentItem = new ListReport();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                reportID = jsonObject.getString("id");
                reportOutlet = jsonObject.getString("outlet");
                reportPaymentMethod = jsonObject.getString("payment");
                reportTotal = jsonObject.getString("total");
                reportTax = jsonObject.getString("tax");
                reportPayable = jsonObject.getString("payable");
                status = jsonObject.getString("status");

                date = dateFormat.parse(jsonObject.getString("date"));

                currentItem.setReportID(Integer.valueOf(reportID));
                currentItem.setReportOutlet(reportOutlet);
                currentItem.setReportPaymentMethod(reportPaymentMethod);
                currentItem.setReportTotal(reportTotal);
                currentItem.setReportTax(reportTax);
                currentItem.setReportPayable(reportPayable);
                currentItem.setReportDate(date);

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean
                    conditionDate = (date.equals(DateFrom) || date.after(DateFrom)) && (date.before(DateTo) || date.equals(DateTo));

            if (reportOutlet.equals(spinnerOne.getSelectedItem().toString()) && reportPaymentMethod.equals(spinnerTwo.getSelectedItem().toString()) &&
                    conditionDate && status.equals("2")) {
                listReports.add(currentItem);
            }
        }

        if (listReports.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchSalesReport(JSONArray jsonArray, List<ListReport> listReports, RecyclerView.Adapter adapter,
                                  RecyclerView recyclerView, EditText inputOne, EditText inputTwo, Spinner spinnerOne, Spinner spinnerTwo) {

        final String
                stringDateFrom = inputOne.getText().toString().trim(),
                stringDateTo = inputTwo.getText().toString().trim();

        try {
            DateFrom = dateFormat.parse(stringDateFrom);
            DateTo = dateFormat.parse(stringDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new SalesReportListAdapter(listReports, this);

        listReports.clear();
        adapter.notifyItemRangeRemoved(0, listReports.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListReport currentItem = new ListReport();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                reportID = jsonObject.getString("id");
                reportOutlet = jsonObject.getString("outlet");
                reportPaymentMethod = jsonObject.getString("payment");
                reportTotal = jsonObject.getString("total");
                reportTax = jsonObject.getString("tax");
                reportPayable = jsonObject.getString("payable");
                status = jsonObject.getString("status");

                date = dateFormat.parse(jsonObject.getString("date"));

                currentItem.setReportID(Integer.valueOf(reportID));
                currentItem.setReportOutlet(reportOutlet);
                currentItem.setReportPaymentMethod(reportPaymentMethod);
                currentItem.setReportTotal(reportTotal);
                currentItem.setReportTax(reportTax);
                currentItem.setReportPayable(reportPayable);
                currentItem.setReportDate(date);

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean
                    conditionDate = (date.equals(DateFrom) || date.after(DateFrom)) && (date.before(DateTo) || date.equals(DateTo));

            if (reportOutlet.equals(spinnerOne.getSelectedItem().toString()) && reportPaymentMethod.equals(spinnerTwo.getSelectedItem().toString()) &&
                    conditionDate && status.equals("1")) {
                listReports.add(currentItem);
            }
        }

        if (listReports.size() == 0) {
            Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(adapter);
    }

    public void searchSoldByProductReport(JSONArray jsonArray, List<ListReport> listReports, RecyclerView.Adapter adapter,
                                          RecyclerView recyclerView, EditText inputOne, EditText inputTwo, EditText inputThree, Spinner spinnerOne, LinearLayout layoutOne) {

        final String
                stringSearch = inputThree.getText().toString().trim(),
                stringDateFrom = inputOne.getText().toString().trim(),
                stringDateTo = inputTwo.getText().toString().trim();

        try {
            DateFrom = dateFormat.parse(stringDateFrom);
            DateTo = dateFormat.parse(stringDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new SoldByProductReportListAdapter(listReports, this);

        listReports.clear();
        adapter.notifyItemRangeRemoved(0, listReports.size());

        for (int i = 0; i < jsonArray.length(); i++) {

            ListReport currentItem = new ListReport();

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                reportID = jsonObject.getString("id");
                reportOutlet = jsonObject.getString("outlet");
                reportPaymentMethod = jsonObject.getString("payment");
                reportItem = jsonObject.getString("item");
                reportCustomer = jsonObject.getString("customer");
                reportTotal = jsonObject.getString("total");
                reportTax = jsonObject.getString("tax");
                reportPayable = jsonObject.getString("payable");
                status = jsonObject.getString("status");

                date = dateFormat.parse(jsonObject.getString("date"));

                currentItem.setReportID(Integer.valueOf(reportID));
                currentItem.setReportOutlet(reportOutlet);
                currentItem.setReportPaymentMethod(reportPaymentMethod);
                currentItem.setReportCustomer(reportCustomer);
                currentItem.setRetportItem(reportItem);
                currentItem.setReportTotal(reportTotal);
                currentItem.setReportTax(reportTax);
                currentItem.setReportPayable(reportPayable);
                currentItem.setReportDate(date);

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean
                    conditionDate = (date.equals(DateFrom) || date.after(DateFrom)) && (date.before(DateTo) || date.equals(DateTo));

            if (inputThree.getText().toString().isEmpty()) {
                if (conditionDate && status.equals("1")) {
                    listReports.add(currentItem);
                }
            } else {
                if ((reportID.contains(stringSearch) || reportOutlet.contains(stringSearch) || reportPaymentMethod.contains(stringSearch)
                        || reportItem.contains(stringSearch) || reportCustomer.contains(stringSearch) || reportTotal.contains(stringSearch)
                        || reportTax.contains(stringSearch) || reportPayable.contains(stringSearch)) && conditionDate && status.equals("1")) {
                    listReports.add(currentItem);
                }
            }
        }

        if (layoutOne.getVisibility() == View.GONE) {
            if (listReports.size() == 0) {
                Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
            }  else {
                layoutOne.setVisibility(View.VISIBLE);
            }
        } else {
            if (listReports.size() == 0) {
                Toast.makeText(MainActivity.this, "Data Is Not Found", Toast.LENGTH_LONG).show();
            }
        }

        recyclerView.setAdapter(adapter);
    }

//    Set Title In Toolbar

    public void setMyTitle(String setTitle) {
        this.title = setTitle;
        toolbar.setTitle(title);
    }

//    Call URL Database

    public String setURLReadData(String condition) {
        switch (condition) {
            case "Customer":
                URL = URL_DATA_READ_CUSTOMER;
                break;
            case "Bill":
                URL = URL_DATA_READ_BILL;
                break;
            case "Bill Item":
                URL = URL_DATA_READ_BILLITEM;
                break;
            case "Debit":
                URL = URL_DATA_READ_DEBIT;
                break;
            case "Gift Card":
                URL = URL_DATA_READ_GIFTCARD;
                break;
            case "Expenses":
                URL = URL_DATA_READ_EXPENSES;
                break;
            case "Expenses Category":
                URL = URL_DATA_READ_EXPENSESCATEGORY;
                break;
            case "Inventory":
                URL = URL_DATA_READ_INVENTORY;
                break;
            case "Payment":
                URL = URL_DATA_READ_PAYMENT;
                break;
            case "Profit And Loss":
                URL = URL_DATA_READ_MONTHLYPROFITANDLOSS;
                break;
            case "Product Category":
                URL = URL_DATA_READ_PRODUCTCATEGORY;
                break;
            case "Product":
                URL = URL_DATA_READ_PRODUCT;
                break;
            case "Purchase Order":
                URL = URL_DATA_READ_PURCHASEORDER;
                break;
            case "Purchase Order Item":
                URL = URL_DATA_READ_PURCHASEORDERITEM;
                break;
            case "Return Order":
                URL = URL_DATA_READ_RETURNORDER;
                break;
            case "Return Order Item":
                URL = URL_DATA_READ_RETURNORDERITEM;
                break;
            case "Sales":
                URL = URL_DATA_READ_SALES;
                break;
            case "Report":
                URL = URL_DATA_READ_REPORT;
                break;
            case "Outlet":
                URL = URL_DATA_READ_OUTLET;
                break;
            case "User":
                URL = URL_DATA_READ_USER;
                break;
            case "Supplier":
                URL = URL_DATA_READ_SUPPLIER;
                break;
            case "Role":
                URL = URL_DATA_READ_ROLE;
                break;
            case "Payment Method":
                URL = URL_DATA_READ_PAYMENTMETHOD;
                break;
        }
        return URL;
    }

    public String setURLAddData(String condition) {
        switch (condition) {
            case "Customer":
                URL = URL_DATA_ADD_CUSTOMER;
                break;
            case "Bill":
                URL = URL_DATA_ADD_BILL;
                break;
            case "Bill Item":
                URL = URL_DATA_ADD_BILLITEM;
                break;
            case "Gift Card":
                URL = URL_DATA_ADD_GIFTCARD;
                break;
            case "Expenses":
                URL = URL_DATA_ADD_EXPENSES;
                break;
            case "Expenses Category":
                URL = URL_DATA_ADD_EXPENSESCATEGORY;
                break;
            case "Outlet":
                URL = URL_DATA_ADD_OUTLET;
                break;
            case "Payment":
                URL = URL_DATA_ADD_PAYMENT;
                break;
            case "Payment Item":
                URL = URL_DATA_ADD_PAYMENTITEM;
                break;
            case "Payment Method":
                URL = URL_DATA_ADD_PAYMENTMETHOD;
                break;
            case "Product Category":
                URL = URL_DATA_ADD_PRODUCTCATEGORY;
                break;
            case "Product":
                URL = URL_DATA_ADD_PRODUCT;
                break;
            case "Purchase Order":
                URL = URL_DATA_ADD_PURCHASEORDER;
                break;
            case "Purchase Order Item":
                URL = URL_DATA_ADD_PURCHASEORDERITEM;
                break;
            case "Return Order":
                URL = URL_DATA_ADD_RETURNORDER;
                break;
            case "Return Order Item":
                URL = URL_DATA_ADD_RETURNORDERITEM;
                break;
            case "Supplier":
                URL = URL_DATA_ADD_SUPPLIER;
                break;
            case "User":
                URL = URL_DATA_ADD_USER;
                break;
        }
        return URL;
    }

    public String setURLDeleteData(String condition) {
        switch (condition) {
            case "Customer":
                URL = URL_DATA_DELETE_CUSTOMER;
                break;
            case "Bill":
                URL = URL_DATA_DELETE_BILL;
                break;
            case "Bill Item":
                URL = URL_DATA_DELETE_BILLITEM;
                break;
            case "Gift Card":
                URL = URL_DATA_DELETE_GIFTCARD;
                break;
            case "Expenses":
                URL = URL_DATA_DELETE_EXPENSES;
                break;
            case "Expenses Category":
                URL = URL_DATA_DELETE_EXPENSESCATEGORY;
                break;
            case "Outlet":
                URL = URL_DATA_DELETE_OUTLET;
                break;
            case "Payment":
                URL = URL_DATA_DELETE_PAYMENT;
                break;
            case "Payment Method":
                URL = URL_DATA_DELETE_PAYMENTMETHOD;
                break;
            case "Product Category":
                URL = URL_DATA_DELETE_PRODUCTCATEGORY;
                break;
            case "Product":
                URL = URL_DATA_DELETE_PRODUCT;
                break;
            case "Purchase Order":
                URL = URL_DATA_DELETE_PURCHASEORDER;
                break;
            case "Purchase Order Item":
                URL = URL_DATA_DELETE_PURCHASEORDERITEM;
                break;
            case "Return Order":
                URL = URL_DATA_DELETE_RETURNORDER;
                break;
            case "Return Order Item":
                URL = URL_DATA_DELETE_RETURNORDERITEM;
                break;
            case "Sales":
                URL = URL_DATA_DELETE_SALES;
                break;
            case "Supplier":
                URL = URL_DATA_DELETE_SUPPLIER;
                break;
            case "User":
                URL = URL_DATA_DELETE_USER;
                break;
        }
        return URL;
    }

    public String setURLUpdateData(String condition) {
        switch (condition) {
            case "Customer":
                URL = URL_DATA_UPDATE_CUSTOMER;
                break;
            case "Bill":
                URL = URL_DATA_UPDATE_BILL;
                break;
            case "Bill Item":
                URL = URL_DATA_UPDATE_BILLITEM;
                break;
            case "Gift Card":
                URL = URL_DATA_UPDATE_GIFTCARD;
                break;
            case "Expenses":
                URL = URL_DATA_UPDATE_EXPENSES;
                break;
            case "Expenses Category":
                URL = URL_DATA_UPDATE_EXPENSESCATEGORY;
                break;
            case "Inventory":
                URL = URL_DATA_UPDATE_INVENTORY;
                break;
            case "Outlet":
                URL = URL_DATA_UPDATE_OUTLET;
                break;
            case "Payment":
                URL = URL_DATA_UPDATE_PAYMENT;
                break;
            case "Payment Method":
                URL = URL_DATA_UPDATE_PAYMENTMETHOD;
                break;
            case "Password":
                URL = URL_DATA_UPDATE_PASSWORD;
                break;
            case "Product Category":
                URL = URL_DATA_UPDATE_PRODUCTCATEGORY;
                break;
            case "Product":
                URL = URL_DATA_UPDATE_PRODUCT;
                break;
            case "Return Order":
                URL = URL_DATA_UPDATE_RETURNORDER;
                break;
            case "Return Order Item":
                URL = URL_DATA_UPDATE_RETURNORDERITEM;
                break;
            case "Supplier":
                URL = URL_DATA_UPDATE_SUPPLIER;
                break;
            case "User":
                URL = URL_DATA_UPDATE_USER;
                break;
        }
        return URL;
    }

//    Setup Navigation Drawer

    private void setupDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                toolbar.setTitle("DigiTiket");

                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toolbar.setTitle(title);

                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_logout) {
            LoginActivity.userCount = 0;
            SharedPreferences user = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            SharedPreferences.Editor editor = user.edit();
            editor.putInt("isLogin", LoginActivity.userCount);
            editor.commit();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_customers:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CustomerFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_dashboard:
                getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new DashboardFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_debit:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new DebitFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_expenses_category:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ExpensesCategoryFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_expenses:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ExpensesFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_inventory:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new InventoryFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_add_gift_card:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddGiftCardFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_list_gift_card:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListGiftCardFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_list_product:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListProductFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_opened_bill:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new OpenedBillFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_outlets_setting:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new OutletsSettingFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_payment_method_setting:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PaymentMethodFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_pos:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosListOutletsFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_product_category:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProductCategoryFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_profit_and_loss:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfitAndLossFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_profit_and_loss_report:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfitAndLossReportsFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_purchase_order:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PurchaseOrderFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_create_return_order:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddReturnOrderFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_return_order_report:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReturnOrderReportsFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_today_sales:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SalesFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_sales_reports:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SalesReportsFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_sold_by_product:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SoldByProductReportsFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_supplier_setting:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SuppliersSettingFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_system_setting:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SystemSettingFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_user_setting:
                getSupportFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new UsersSettingFragment()).addToBackStack("Fragment").commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}