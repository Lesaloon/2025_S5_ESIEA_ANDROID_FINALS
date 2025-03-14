package edu.esiea.a2025_s5_esiea_android_finals.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import edu.esiea.a2025_s5_esiea_android_finals.R;
import edu.esiea.a2025_s5_esiea_android_finals.ViewModel.PlaceViewModel;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.AccomodationTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.CulturalTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.EntertainmentTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.PriceRange;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RelaxationTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RestaurantTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.SportsTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.CulturalVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Place;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.SportsVenue;

import java.util.ArrayList;
import java.util.List;

public class CreatePlaceFragment extends Fragment {

    private enum PlaceType {
        ACCOMODATION("Accommodation"),
        RESTAURANT("Restaurant"),
        CULTURAL_VENUE("Cultural Venue"),
        ENTERTAINMENT_VENUE("Entertainment Venue"),
        RELAXATION_VENUE("Relaxation Venue"),
        SPORTS_VENUE("Sports Venue");

        private final String displayName;

        PlaceType(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    private PlaceViewModel viewModel;
    private TextInputEditText nameInput, descriptionInput, phoneInput, emailInput, websiteInput, latitudeInput, longitudeInput;
    private AutoCompleteTextView placeTypeDropdown;
    private LinearLayout dynamicFieldsContainer;
    private MaterialButton selectLocationButton, saveButton;
    
    private PlaceType selectedPlaceType;

    private double latitude, longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);

        // Initialize UI components
        initializeViews(view);
        setupPlaceTypeDropdown();
        setupButtons();
        
        // Check if coordinates were passed from MapFragment
        Bundle args = getArguments();
        if (args != null && args.containsKey("latitude") && args.containsKey("longitude")) {
            // Fill in latitude and longitude fields
            double latitude = args.getDouble("latitude");
            double longitude = args.getDouble("longitude");
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    private void initializeViews(View view) {
        nameInput = view.findViewById(R.id.name_input);
        descriptionInput = view.findViewById(R.id.description_input);
        phoneInput = view.findViewById(R.id.phone_input);
        emailInput = view.findViewById(R.id.email_input);
        websiteInput = view.findViewById(R.id.website_input);
        placeTypeDropdown = view.findViewById(R.id.place_type_dropdown);
        dynamicFieldsContainer = view.findViewById(R.id.dynamic_fields_container);
        selectLocationButton = view.findViewById(R.id.select_location_button);
        saveButton = view.findViewById(R.id.save_button);
    }

    private void setupPlaceTypeDropdown() {
        PlaceType[] placeTypes = PlaceType.values();
        String[] placeTypeNames = new String[placeTypes.length];
        
        for (int i = 0; i < placeTypes.length; i++) {
            placeTypeNames[i] = placeTypes[i].toString();
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                placeTypeNames
        );
        
        placeTypeDropdown.setAdapter(adapter);
        
        // Using hint from TextInputLayout instead of setting it directly on AutoCompleteTextView
        // This helps with accessibility as the hint becomes the label when filled
        
        // Make non-editable - this improves accessibility since it's intended as a selector, not a free text field
        placeTypeDropdown.setKeyListener(null);
        
        // Set the dropdown to show when clicking on it
        placeTypeDropdown.setOnClickListener(v -> placeTypeDropdown.showDropDown());
        
        // Handle selection
        placeTypeDropdown.setOnItemClickListener((parent, view, position, id) -> {
            selectedPlaceType = PlaceType.values()[position];
            updateDynamicFields();
            
            // Set the content description to include the selected value for better accessibility
            View parentLayout = (View) placeTypeDropdown.getParent().getParent();
            String selection = placeTypeNames[position];
            parentLayout.announceForAccessibility(getString(R.string.place_type) + " " + selection + " selected");
        });
    }

    private void setupButtons() {
        selectLocationButton.setOnClickListener(v -> {
            // Go back to map for location selection
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                savePlaceToDatabase();
            }
        });
    }

    private void updateDynamicFields() {
        // Clear previous dynamic fields
        dynamicFieldsContainer.removeAllViews();

        if (selectedPlaceType == null) return;

        switch (selectedPlaceType) {
            case ACCOMODATION:
                addAccommodationFields();
                break;
            case RESTAURANT:
                addRestaurantFields();
                break;
            case CULTURAL_VENUE:
            case ENTERTAINMENT_VENUE:
            case RELAXATION_VENUE:
                addVenueFields();
                break;
            case SPORTS_VENUE:
                addSportsVenueFields();
                break;
        }
    }

    private void addAccommodationFields() {
        // Add nightly rate field
        TextInputLayout rateLayout = createTextInputLayout("Minimum Nightly Rate", "minNightlyRate");
        TextInputEditText rateInput = new TextInputEditText(requireContext());
        rateInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        rateLayout.addView(rateInput);
        dynamicFieldsContainer.addView(rateLayout);

        // Add accommodation tags
        ChipGroup tagGroup = createChipGroupForEnum("Accommodation Tags", AccomodationTag.values());
        dynamicFieldsContainer.addView(tagGroup);
    }

    private void addRestaurantFields() {
        // Add cuisine origin field
        TextInputLayout cuisineLayout = createTextInputLayout("Cuisine Origin", "cuisineOrigin");
        TextInputEditText cuisineInput = new TextInputEditText(requireContext());
        cuisineLayout.addView(cuisineInput);
        dynamicFieldsContainer.addView(cuisineLayout);

        // Add price range dropdown
        TextInputLayout priceLayout = createDropdownLayout("Price Range", "priceRange");
        AutoCompleteTextView priceDropdown = new AutoCompleteTextView(requireContext());
        priceDropdown.setInputType(android.text.InputType.TYPE_NULL);
        
        String[] priceRanges = new String[PriceRange.values().length];
        for (int i = 0; i < PriceRange.values().length; i++) {
            priceRanges[i] = PriceRange.values()[i].name();
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                priceRanges
        );
        priceDropdown.setAdapter(adapter);
        priceLayout.addView(priceDropdown);
        dynamicFieldsContainer.addView(priceLayout);

        // Add restaurant tags
        ChipGroup tagGroup = createChipGroupForEnum("Restaurant Tags", RestaurantTag.values());
        dynamicFieldsContainer.addView(tagGroup);
    }

    private void addVenueFields() {
        // Add opening hours
        TextInputLayout openingLayout = createTextInputLayout("Opening Hours", "openingHours");
        TextInputEditText openingInput = new TextInputEditText(requireContext());
        openingLayout.addView(openingInput);
        dynamicFieldsContainer.addView(openingLayout);

        // Add closing hours
        TextInputLayout closingLayout = createTextInputLayout("Closing Hours", "closingHours");
        TextInputEditText closingInput = new TextInputEditText(requireContext());
        closingLayout.addView(closingInput);
        dynamicFieldsContainer.addView(closingLayout);

        // Add entry fee
        TextInputLayout feeLayout = createTextInputLayout("Entry Fee", "entryFee");
        TextInputEditText feeInput = new TextInputEditText(requireContext());
        feeInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        feeLayout.addView(feeInput);
        dynamicFieldsContainer.addView(feeLayout);

        // Add specific venue tags based on type
        ChipGroup tagGroup;
        switch (selectedPlaceType) {
            case CULTURAL_VENUE:
                tagGroup = createChipGroupForEnum("Cultural Tags", CulturalTag.values());
                break;
            case ENTERTAINMENT_VENUE:
                tagGroup = createChipGroupForEnum("Entertainment Tags", EntertainmentTag.values());
                break;
            case RELAXATION_VENUE:
                tagGroup = createChipGroupForEnum("Relaxation Tags", RelaxationTag.values());
                break;
            default:
                return;
        }
        dynamicFieldsContainer.addView(tagGroup);
    }

    private void addSportsVenueFields() {
        // Add opening hours
        TextInputLayout openingLayout = createTextInputLayout("Opening Hours", "openingHours");
        TextInputEditText openingInput = new TextInputEditText(requireContext());
        openingLayout.addView(openingInput);
        dynamicFieldsContainer.addView(openingLayout);

        // Add closing hours
        TextInputLayout closingLayout = createTextInputLayout("Closing Hours", "closingHours");
        TextInputEditText closingInput = new TextInputEditText(requireContext());
        closingLayout.addView(closingInput);
        dynamicFieldsContainer.addView(closingLayout);

        // Add entry fee
        TextInputLayout feeLayout = createTextInputLayout("Entry Fee", "entryFee");
        TextInputEditText feeInput = new TextInputEditText(requireContext());
        feeInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        feeLayout.addView(feeInput);
        dynamicFieldsContainer.addView(feeLayout);

        // Add subscription required checkbox
        MaterialCheckBox subscriptionCheckbox = new MaterialCheckBox(requireContext());
        subscriptionCheckbox.setText("Subscription Required");
        subscriptionCheckbox.setId(View.generateViewId());
        dynamicFieldsContainer.addView(subscriptionCheckbox);

        // Add sports tags
        ChipGroup tagGroup = createChipGroupForEnum("Sports Tags", SportsTag.values());
        dynamicFieldsContainer.addView(tagGroup);
    }

    private TextInputLayout createTextInputLayout(String hint, String tag) {
        TextInputLayout layout = new TextInputLayout(requireContext());
        layout.setHint(hint);
        layout.setTag(tag);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ((LinearLayout.LayoutParams) layout.getLayoutParams()).setMargins(0, 0, 0, 
                (int) (16 * getResources().getDisplayMetrics().density)); // 16dp margin bottom
        return layout;
    }

    private TextInputLayout createDropdownLayout(String hint, String tag) {
        TextInputLayout layout = new TextInputLayout(requireContext());
        layout.setHint(hint);
        layout.setTag(tag);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ((LinearLayout.LayoutParams) layout.getLayoutParams()).setMargins(0, 0, 0, 
                (int) (16 * getResources().getDisplayMetrics().density)); // 16dp margin bottom
        return layout;
    }

    private <T extends Enum<?>> ChipGroup createChipGroupForEnum(String title, T[] enumValues) {
        // Create heading
        TextInputLayout titleLayout = createTextInputLayout(title, "tag_group");
        dynamicFieldsContainer.addView(titleLayout);
        
        // Create chip group for tags
        ChipGroup chipGroup = new ChipGroup(requireContext());
        chipGroup.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        chipGroup.setTag("tag_chips");
        
        // Add chip for each tag
        for (T tag : enumValues) {
            Chip chip = new Chip(requireContext());
            chip.setText(tag.name());
            chip.setTag(tag);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }
        
        return chipGroup;
    }

    private boolean validateInputs() {
        // Basic validation - Add more specific validations as needed
        boolean isValid = true;
        
        if (nameInput.getText().toString().trim().isEmpty()) {
            nameInput.setError(getString(R.string.error_name_required));
            isValid = false;
        }
        
        if (descriptionInput.getText().toString().trim().isEmpty()) {
            descriptionInput.setError(getString(R.string.error_description_required));
            isValid = false;
        }

        
        if (selectedPlaceType == null) {
            placeTypeDropdown.setError(getString(R.string.error_place_type_required));
            isValid = false;
        }
        
        return isValid;
    }

    private void savePlaceToDatabase() {
        try {
            Place place = createPlaceBasedOnType();

            if (place != null) {
                // Currently only Accommodation is fully implemented in the database
                if (place instanceof Accomodation) {
                    viewModel.insertAccommodation((Accomodation) place);
                    Toast.makeText(requireContext(), getString(R.string.place_saved), Toast.LENGTH_SHORT).show();
                } else {
                    // For other types, show a message (you may extend the database later)
                    Toast.makeText(requireContext(), "Entity created but database implementation is pending", Toast.LENGTH_SHORT).show();
                }
                // After saving, navigate back to the MapFragment so the new marker will be displayed
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    getString(R.string.error_saving_place, e.getMessage()),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private Place createPlaceBasedOnType() {
        String name = nameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String website = websiteInput.getText().toString().trim();
        float latitude = (float) this.latitude;
        float longitude = (float) this.longitude;
        
        Place place = null;
        
        switch (selectedPlaceType) {
            case ACCOMODATION:
                place = createAccommodation(name, description, phone, email, website, latitude, longitude);
                break;
            case RESTAURANT:
                place = createRestaurant(name, description, phone, email, website, latitude, longitude);
                break;
            case CULTURAL_VENUE:
                place = createCulturalVenue(name, description, phone, email, website, latitude, longitude);
                break;
            case ENTERTAINMENT_VENUE:
                place = createEntertainmentVenue(name, description, phone, email, website, latitude, longitude);
                break;
            case RELAXATION_VENUE:
                place = createRelaxationVenue(name, description, phone, email, website, latitude, longitude);
                break;
            case SPORTS_VENUE:
                place = createSportsVenue(name, description, phone, email, website, latitude, longitude);
                break;
        }
        
        return place;
    }

    private Accomodation createAccommodation(String name, String description, String phone, String email, String website, float latitude, float longitude) {
        Accomodation accommodation = new Accomodation();
        accommodation.name = name;
        accommodation.description = description;
        accommodation.phoneNumber = phone;
        accommodation.email = email;
        accommodation.websiteURL = website;
        accommodation.latitude = latitude;
        accommodation.longitude = longitude;
        
        // Get min nightly rate
        TextInputEditText rateInput = findInputByTag("minNightlyRate");
        if (rateInput != null && !rateInput.getText().toString().isEmpty()) {
            accommodation.minNightlyRate = Float.parseFloat(rateInput.getText().toString());
        }
        
        // Get selected tags
        accommodation.tags = getSelectedTags(AccomodationTag.class);
        
        return accommodation;
    }

    private Restaurant createRestaurant(String name, String description, String phone, String email, String website, float latitude, float longitude) {
        Restaurant restaurant = new Restaurant();
        restaurant.name = name;
        restaurant.description = description;
        restaurant.phoneNumber = phone;
        restaurant.email = email;
        restaurant.websiteURL = website;
        restaurant.latitude = latitude;
        restaurant.longitude = longitude;
        
        // Get cuisine origin
        TextInputEditText cuisineInput = findInputByTag("cuisineOrigin");
        if (cuisineInput != null) {
            restaurant.cuisineOrigin = cuisineInput.getText().toString();
        }
        
        // Get price range
        AutoCompleteTextView priceInput = findDropdownByTag("priceRange");
        if (priceInput != null && !priceInput.getText().toString().isEmpty()) {
            restaurant.priceRange = PriceRange.valueOf(priceInput.getText().toString());
        }
        
        // Get selected tags
        restaurant.tags = getSelectedTags(RestaurantTag.class);
        
        return restaurant;
    }

    private CulturalVenue createCulturalVenue(String name, String description, String phone, String email, String website, float latitude, float longitude) {
        CulturalVenue venue = new CulturalVenue();
        venue.name = name;
        venue.description = description;
        venue.phoneNumber = phone;
        venue.email = email;
        venue.websiteURL = website;
        venue.latitude = latitude;
        venue.longitude = longitude;
        
        populateVenueFields(venue);
        venue.tags = getSelectedTags(CulturalTag.class);
        
        return venue;
    }

    private EntertainmentVenue createEntertainmentVenue(String name, String description, String phone, String email, String website, float latitude, float longitude) {
        EntertainmentVenue venue = new EntertainmentVenue();
        venue.name = name;
        venue.description = description;
        venue.phoneNumber = phone;
        venue.email = email;
        venue.websiteURL = website;
        venue.latitude = latitude;
        venue.longitude = longitude;
        
        populateVenueFields(venue);
        venue.tags = getSelectedTags(EntertainmentTag.class);
        
        return venue;
    }

    private RelaxationVenue createRelaxationVenue(String name, String description, String phone, String email, String website, float latitude, float longitude) {
        RelaxationVenue venue = new RelaxationVenue();
        venue.name = name;
        venue.description = description;
        venue.phoneNumber = phone;
        venue.email = email;
        venue.websiteURL = website;
        venue.latitude = latitude;
        venue.longitude = longitude;
        
        populateVenueFields(venue);
        venue.tags = getSelectedTags(RelaxationTag.class);
        
        return venue;
    }

    private SportsVenue createSportsVenue(String name, String description, String phone, String email, String website, float latitude, float longitude) {
        SportsVenue venue = new SportsVenue();
        venue.name = name;
        venue.description = description;
        venue.phoneNumber = phone;
        venue.email = email;
        venue.websiteURL = website;
        venue.latitude = latitude;
        venue.longitude = longitude;
        
        populateVenueFields(venue);
        
        // Get subscription required
        MaterialCheckBox subscriptionCheckbox = null;
        for (int i = 0; i < dynamicFieldsContainer.getChildCount(); i++) {
            View child = dynamicFieldsContainer.getChildAt(i);
            if (child instanceof MaterialCheckBox && 
                    ((MaterialCheckBox) child).getText().toString().contains("Subscription")) {
                subscriptionCheckbox = (MaterialCheckBox) child;
                break;
            }
        }
        
        if (subscriptionCheckbox != null) {
            venue.SubscriptionRequired = subscriptionCheckbox.isChecked();
        }
        
        venue.tags = getSelectedTags(SportsTag.class);
        
        return venue;
    }

    private <T> void populateVenueFields(T venue) {
        try {
            // Get opening hours
            TextInputEditText openingInput = findInputByTag("openingHours");
            if (openingInput != null) {
                venue.getClass().getField("openingHours").set(venue, openingInput.getText().toString());
            }
            
            // Get closing hours
            TextInputEditText closingInput = findInputByTag("closingHours");
            if (closingInput != null) {
                venue.getClass().getField("closingHours").set(venue, closingInput.getText().toString());
            }
            
            // Get entry fee
            TextInputEditText feeInput = findInputByTag("entryFee");
            if (feeInput != null && !feeInput.getText().toString().isEmpty()) {
                venue.getClass().getField("entryFee").set(venue, Float.parseFloat(feeInput.getText().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextInputEditText findInputByTag(String tag) {
        for (int i = 0; i < dynamicFieldsContainer.getChildCount(); i++) {
            View child = dynamicFieldsContainer.getChildAt(i);
            if (child instanceof TextInputLayout && tag.equals(child.getTag())) {
                for (int j = 0; j < ((TextInputLayout) child).getChildCount(); j++) {
                    View grandchild = ((TextInputLayout) child).getChildAt(j);
                    if (grandchild instanceof TextInputEditText) {
                        return (TextInputEditText) grandchild;
                    }
                }
            }
        }
        return null;
    }

    private AutoCompleteTextView findDropdownByTag(String tag) {
        for (int i = 0; i < dynamicFieldsContainer.getChildCount(); i++) {
            View child = dynamicFieldsContainer.getChildAt(i);
            if (child instanceof TextInputLayout && tag.equals(child.getTag())) {
                for (int j = 0; j < ((TextInputLayout) child).getChildCount(); j++) {
                    View grandchild = ((TextInputLayout) child).getChildAt(j);
                    if (grandchild instanceof AutoCompleteTextView) {
                        return (AutoCompleteTextView) grandchild;
                    }
                }
            }
        }
        return null;
    }

    private <T extends Enum<T>> List<T> getSelectedTags(Class<T> enumType) {
        List<T> selectedTags = new ArrayList<>();
        
        // Find the chip group
        ChipGroup chipGroup = null;
        for (int i = 0; i < dynamicFieldsContainer.getChildCount(); i++) {
            View child = dynamicFieldsContainer.getChildAt(i);
            if (child instanceof ChipGroup && "tag_chips".equals(child.getTag())) {
                chipGroup = (ChipGroup) child;
                break;
            }
        }
        
        if (chipGroup != null) {
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                View child = chipGroup.getChildAt(i);
                if (child instanceof Chip) {
                    Chip chip = (Chip) child;
                    if (chip.isChecked() && chip.getTag() instanceof Enum && 
                            enumType.isAssignableFrom(chip.getTag().getClass())) {
                        selectedTags.add(enumType.cast(chip.getTag()));
                    }
                }
            }
        }
        
        return selectedTags;
    }
}