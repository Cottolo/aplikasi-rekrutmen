use tm_data_pegawai;

CREATE TABLE biodata
(
   email varchar(100) not null,
   password varchar(100) not null,
   token varchar(100),
   token_expired_at bigint,
   role varchar(100),

   posisi varchar(100),
   nama varchar(100),
   nik varchar(100),
   ttl varchar(100),
   jenis_kelamin varchar(100),
   agama varchar(100),
   golongan_darah varchar(100),
   status varchar(100),
   alamat_ktp varchar(100),
   alamat_tinggal varchar(100),
   no_telp varchar(100),
   orang_terdekat varchar(100),
   skill varchar(100),
   bersedia_ditempatkan varchar(100),
   penghasilan_diharapkan varchar(100),

   primary key (email),
   UNIQUE (token)
) ENGINE InnoDB;

select * from biodata;

update biodata set
role = 'admin' where
email = 'admin@mail.com';

delete from biodata where email = 'test@mail.com';

desc biodata;

drop table biodata;

--============================================================

CREATE TABLE pendidikan_terakhir
(
   id varchar(50) not null,
   email varchar(100) not null,
   jenjang_pendidikan varchar(100),
   nama_institusi varchar(100),
   jurusan varchar(100),
   tahun_lulus varchar(100),
   ipk varchar(100),
   primary key (id),
   foreign key fk_id_pendidikan (email) references biodata(email)
) ENGINE InnoDB;

select * from pendidikan_terakhir;

desc pendidikan_terakhir;

drop table pendidikan_terakhir;

--============================================================

CREATE TABLE riwayat_pelatihan
(
   id varchar(50) not null,
   email varchar(100) not null,
   nama_pelatihan varchar(100),
   sertifikat varchar(100),
   tahun varchar(100),

   primary key (id),
   foreign key fk_id_pelatihan (email) references biodata(email)
) ENGINE InnoDB;

select * from riwayat_pelatihan;

desc riwayat_pelatihan;

drop table riwayat_pelatihan;

--============================================================


CREATE TABLE riwayat_pekerjaan
(
   id varchar(50) not null,
   email varchar(100) not null,
   nama_perusahaan varchar(100),
   posisi_terakhir varchar(100),
   pendapatan_terakhir varchar(100),
   tahun varchar(100),

   primary key (id),
   foreign key fk_id_pekerjaan (email) references biodata(email)
) ENGINE InnoDB;

select * from riwayat_pekerjaan;

desc riwayat_pekerjaan;

drop table riwayat_pekerjaan;