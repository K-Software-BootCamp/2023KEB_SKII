# data.py

import pickle as pkl
import torch

from torch.utils.data import Dataset, DataLoader


class PHMTestDataset_Sequential(Dataset):
    """PHM data set where each item is a sequence"""
    def __init__(self, dataset, seq_len=5):
        """
        dataset_id = index of dataset to read from
        indices = indices from the dataset that need to be included
        seq_len = length of the output sequence
        i.e., return a sequence of length `seq_len` from the dataset as `x` and fault probability of the last frame as `y`
        """
        self.data = dataset
        self.seq_len = seq_len
    
    def __len__(self):
        return self.data['x'].shape[0]-self.seq_len+1
    
    def __getitem__(self, i):
        sample = {'x': torch.from_numpy(self.data['x'][i:i+self.seq_len])}
        return sample

def load_data_from_pfile(file_path):
    """
    Load the data from the file_path.
    Args:
        file: The file containing the data.
    Returns:
        The data.
    """
    with open(file_path, 'rb') as pfile:
        sample_data = pkl.load(pfile)
    return sample_data
