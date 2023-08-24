'''
데이터를 읽어오는 util입니다. merge_csv.ipynb사용 후 다음 순서로 진행
from utils import datareader

# CSV 파일들이 있는 경로
csv_folder_path = '/content/drive/MyDrive/merged_dataset'

# csv 전부다 불러오기
Bearings = load_data.load_csv(csv_folder_path)
# 원하는 csv만 불러오기
Bearings = load_data.load_csv(csv_folder_path, ['Learning_set_Bearing1_1.csv'])

# 읽어온 bearing 이름 확인
Bearings.get_bearing_name()

# 예시: 'Learning_set_Bearing1_1'에 해당하는 데이터 출력
file1_data = Bearings.get_df('Learning_set_Bearing1_1')

print(file1_data.head())
'''
import os
import pandas as pd


class DataReader:
    def __init__(self, folder_path, selection=None):
        self.folder_path = folder_path
        self.csv_data = {}
        self.selection = selection

    def read_csv_files(self):
        if self.selection == None:
            csv_files = [file for file in os.listdir(
                self.folder_path) if file.endswith('.csv')]
        else:
            csv_files = self.selection
        for csv_file in csv_files:
            print('now read', csv_file)
            file_path = os.path.join(self.folder_path, csv_file)
            df = pd.read_csv(file_path)
            var_name = csv_file.replace('.csv', '')
            self.csv_data[var_name] = df

    def get_bearing_name(self):
        return list(self.csv_data.keys())

    def get_df(self, var_name):
        return self.csv_data.get(var_name)


def load_csv(csv_folder_path, selection=None):
    # DataReader 인스턴스 생성
    Bearings = DataReader(csv_folder_path, selection)
    Bearings.read_csv_files()

    return Bearings
