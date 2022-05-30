package br.univali.contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import br.univali.contacts.databinding.FragmentContactDetailsBinding;

public class ContactDetailsFragment extends Fragment {
    private FragmentContactDetailsBinding binding;
    private ContactDetailViewModel viewModel;
    private Contact contact;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContactDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false);
        contact = (Contact) requireArguments().getSerializable("contact");
        if (contact != null) {
            assert (binding.name.getEditText() != null);
            assert (binding.phone.getEditText() != null);
            binding.name.getEditText().setText(contact.getName());
            binding.phone.getEditText().setText(contact.getPhone());
            binding.workPhone.getEditText().setText(contact.getWorkPhone());
            binding.homePhone.getEditText().setText(contact.getHomePhone());
        } else {
            binding.buttonDelete.setVisibility(View.GONE);
        }
        binding.buttonSave.setOnClickListener(v -> {
            if (!validate()) return;
            if (contact == null) {
                contact = new Contact();
                contact.setName(binding.name.getEditText().getText().toString());
                contact.setPhone(binding.phone.getEditText().getText().toString());
                contact.setWorkPhone(binding.phone.getEditText().getText().toString());
                contact.setHomePhone(binding.phone.getEditText().getText().toString());
                viewModel.add(requireContext(), contact);
            } else {
                contact.setName(binding.name.getEditText().getText().toString());
                contact.setPhone(binding.phone.getEditText().getText().toString());
                contact.setWorkPhone(binding.phone.getEditText().getText().toString());
                contact.setHomePhone(binding.phone.getEditText().getText().toString());
                viewModel.update(requireContext(), contact);
            }
            Navigation.findNavController(v).navigate(R.id.action_from_contact_details_to_contact_list);
        });
        binding.buttonDelete.setOnClickListener(v -> {
            viewModel.delete(requireContext(), contact);
            Navigation.findNavController(v).navigate(R.id.action_from_contact_details_to_contact_list);
        });
        return binding.getRoot();
    }

    private boolean validate() {
        String error = null;
        if (binding.name.getEditText().getText().toString().isEmpty()) {
            error = "Nome está vazio!";
        } else if (binding.phone.getEditText().getText().toString().isEmpty()) {
            error = "Telefone está vazio!";
        } else if (binding.workPhone.getEditText().getText().toString().isEmpty()) {
            error = "Telefone do trabalho está vazio!";
        } else if (binding.homePhone.getEditText().getText().toString().isEmpty()) {
        error = "Telefone de casa está vazio!";
        }
        if (error != null) {
            Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
        }
        return error == null;
    }
}
