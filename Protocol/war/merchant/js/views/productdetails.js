directory.ProductView = Backbone.View.extend({

    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }
});

directory.ProductManagementView = Backbone.View.extend({

    render: function () {
        this.$el.html(this.template());
        return this;
    },
    
    createProduct: function()
    {
    	this.updateSaveModel();
    },
    
    events: {
    	"click #saveProduct" : this.createProduct
    }
});

directory.ProductSummaryView = Backbone.View.extend({

    initialize:function (options) {
    	_.bindAll(this);
    	this.model = new directory.Product();
        this.model.on("change", this.render, this);
        this.collection = options.collection;
        this.isMerchantView = options.isMerchantView;
    },

    render:function () {
    	
        this.$el.html(this.template(this.model.toJSON()));
        
        if(this.isMerchantView)
        	this.$el.find('.controlsContainer').html(new directory.ProductManagementView().render().el);
        
        $name = this.$el.find('#name');
        $desc = this.$el.find('#description');
        $price = this.$el.find('#price');
        $cert = this.$el.find('#cert');
        
        //this.$("#createProduct").click(this.createProduct);

        return this;
    },

    updateSaveModel:function()
    {
    	var data = {
    			name : $name.val(),
    			description : $desc.val(),
    			price: parseInt($price.val()),
    			cert : $cert.val()
    	};
    	this.model.save(data);
    	this.collection.add(this.model);
    },

    setModel:function(newModel)
    {
    	this.model = newModel;
    	this.render();
    }
});